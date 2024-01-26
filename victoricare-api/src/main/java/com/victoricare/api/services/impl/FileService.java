package com.victoricare.api.services.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.victoricare.api.configurations.S3Config;
import com.victoricare.api.enums.EAudioExtention;
import com.victoricare.api.enums.EIMGExtention;
import com.victoricare.api.enums.EMediaType;
import com.victoricare.api.enums.EMessage;
import com.victoricare.api.enums.EVideoExtention;
import com.victoricare.api.exceptions.InternalServerException;
import com.victoricare.api.exceptions.InvalidInputException;
import com.victoricare.api.services.IFileService;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.util.Base64;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FileService implements IFileService {

    @Autowired
    private S3Config s3Config;

    public static enum PropKey {
        FILE,
        FILE_NAME,
        FILE_EXTENTION,
        FOLDER_NAME
    }

    @Override
    public Map<PropKey, Object> doUpload(MultipartFile mFile, String type) {
        Map<PropKey, Object> properties = new HashMap<PropKey, Object>();

        if (mFile == null || mFile.isEmpty()) {
            throw new InvalidInputException(EMessage.INVALID_FILE);
        }
        this.createOnDisc(properties, mFile);
        this.computeFolder(type, properties);
        this.doSave(properties);
        return properties;
    }

    private void createOnDisc(Map<PropKey, Object> properties, MultipartFile mFile) {
        try {
            String fileName = mFile.getOriginalFilename();
            properties.put(PropKey.FILE_NAME, fileName);

            // Create a temporary file
            File file = new File(System.getProperty("java.io.tmpdir") + "/" + fileName);

            // Transfer the content of the MultipartFile to the File
            mFile.transferTo(file);
            properties.put(PropKey.FILE, file);
        } catch (IOException e) {
            log.error("FAIL! -> Message: " + e.getMessage());
            throw new InternalServerException(EMessage.FILE_CANNOT_BE_CREATED);
        }
    }

    private void computeFolder(String type, Map<PropKey, Object> properties) {

        String fileName = (String) properties.get(PropKey.FILE_NAME);

        if (fileName != null && fileName.contains(".")) {

            String extention = fileName.substring(fileName.lastIndexOf('.') + 1);
            properties.put(PropKey.FILE_EXTENTION, extention);

            if (EMediaType.get(type) == EMediaType.IMAGE && this.isImage(extention)) {
                properties.put(PropKey.FOLDER_NAME, this.s3Config.getImagesFolder());

            } else if (EMediaType.get(type) == EMediaType.AUDIO && this.isAudio(extention)) {
                properties.put(PropKey.FOLDER_NAME, this.s3Config.getAudiosFolder());

            } else if (EMediaType.get(type) == EMediaType.VIDEO && this.isVideo(extention)) {
                properties.put(PropKey.FOLDER_NAME, this.s3Config.getVideosFolder());

            } else {
                throw new InvalidInputException(EMessage.UNSUPPORTED_FILE_TYPE);
            }

        } else {
            throw new InvalidInputException(EMessage.INVALID_FILE_NAME);
        }
    }

    private boolean isImage(String extention) {
        return EIMGExtention.get(extention) != EIMGExtention.UNSUPPORTED;
    }

    private boolean isVideo(String extention) {
        return EVideoExtention.get(extention) != EVideoExtention.UNSUPPORTED;
    }

    private boolean isAudio(String extention) {
        return EAudioExtention.get(extention) != EAudioExtention.UNSUPPORTED;
    }

    @Override
    public String doGetUrl(String folderName, final String filename) {
        return this.s3Config.getS3BucketUrl() + folderName + "/" + filename;
    }

    // @Async annotation ensures that the method is executed in a different thread
    @Async
    private S3ObjectInputStream doGetInputStream(String folderName, String fileName) {
        try {
            return this.s3Config
                    .getAmazonS3Client()
                    .getObject(this.s3Config.getS3BucketName(), folderName + "/" + fileName)
                    .getObjectContent();
        } catch (Exception e) {
            log.error("FAIL! -> Message: " + e.getMessage());
            throw e;
            // throw new InternalServerException(EMessage.FILE_NOT_BE_DOWNLOADED);
        }
    }

    @Override
    @Async
    public String doGetBase64(String folderName, String fileName) {

        S3ObjectInputStream inputStream = this.doGetInputStream(folderName, fileName);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            byte[] fileBytes = outputStream.toByteArray();
            String base64 = Base64.getEncoder().encodeToString(fileBytes);

            // Assuming the object is a text file, adjust MIME type as needed
            String mimeType = "application/octet-stream"; // Change this according to your file type
            return "data:" + mimeType + ";base64," + base64;
        } catch (IOException e) {
            log.error("FAIL! -> Message: " + e.getMessage());
            throw new InternalServerException(EMessage.FILE_CANNNOT_BE_CONVERTED);
        } finally {
            this.closeStream(inputStream);
            this.closeStream(outputStream);
        }
    }

    private void closeStream(Closeable stream) {
        if (stream != null) {
            try {
                stream.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    @Async
    private void doSave(Map<PropKey, Object> properties) {
        try {
            final String extention = (String) properties.get(PropKey.FILE_EXTENTION);
            final String folderName = (String) properties.get(PropKey.FOLDER_NAME);
            final File file = (File) properties.get(PropKey.FILE);

            final String fileName = LocalDateTime.now() + "_" + UUID.randomUUID().toString() + "."
                    + extention.toLowerCase();

            final PutObjectRequest putObjectRequest = new PutObjectRequest(this.s3Config.getS3BucketName(),
                    folderName + "/" + fileName, file);

            this.s3Config.getAmazonS3Client().putObject(putObjectRequest);
            try {
                Files.delete(file.toPath());
            } catch (Exception e) {
                log.error("Error {} occurred while deleting temporary file", e.getLocalizedMessage());
            } // Remove the file locally created in the project folder
            properties.put(PropKey.FILE_NAME, fileName);

        } catch (AmazonServiceException e) {
            log.error("Error {} occurred while uploading file", e.getLocalizedMessage());
            throw new InternalServerException(EMessage.FILE_CANNOT_BE_UPLOADED);

        } catch (Exception ex) {
            log.error("Error {} occurred while processing file", ex.getLocalizedMessage());
            throw new InternalServerException(EMessage.FILE_CANNOT_BE_UPLOADED);
        }
    }

    @Override
    public void doDelete(final String folder, final String fileName) {
        if (fileName == null || folder == null) {
            throw new InvalidInputException(EMessage.INVALID_FILE);
        }
        try {
            final DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(this.s3Config.getS3BucketName(),
                    folder + fileName);
            this.s3Config.getAmazonS3Client().deleteObject(deleteObjectRequest);

        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            throw new InternalServerException(EMessage.FILE_CANNOT_BE_DELETED);
        }
    }
}
