package com.victoricare.api.services.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.UUID;
import com.victoricare.api.exceptions.ResourceNotFoundException;
import com.victoricare.api.services.IFileService;
import com.victoricare.api.enums.EMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.amazonaws.services.s3.AmazonS3;
import com.victoricare.api.enums.EFolder;
import com.victoricare.api.enums.EMediaTypes;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import org.springframework.scheduling.annotation.Async;
import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDateTime;

@Data
@Slf4j
@Service
public class FileService implements IFileService {
    @Autowired
    private AmazonS3 amazonS3;

    private String s3BucketUrl;

    private String s3BucketName;

    private final String baseFolder = "images/";

    public static String AVATAR = "users/avatar.png";

    public void setS3BucketName(final String s3bucketName) {
        this.s3BucketName = s3bucketName;
    }

    public void setS3BucketUrl(final String s3bucketUrl) {
        this.s3BucketUrl = s3bucketUrl;
    }

    private File convertMultiPartFileToFile(final MultipartFile multipartFile) {
        final File file = new File(multipartFile.getOriginalFilename());
        try (final FileOutputStream outputStream = new FileOutputStream(file)) {
            outputStream.write(multipartFile.getBytes());
        } catch (IOException e) {
            log.error("Error {} occurred while converting the multipart file", e.getLocalizedMessage());
        }
        return file;
    }

    public boolean isImage(String ext) {
        return List.of(EMediaTypes.GIF,
                EMediaTypes.JFIF,
                EMediaTypes.JPEG,
                EMediaTypes.JPG,
                EMediaTypes.PJP,
                EMediaTypes.PJPEG,
                EMediaTypes.PNG)
                .contains(EMediaTypes.get(ext));
    }

    @Override
    public String getFileUrl(final String filename) {
        return this.s3BucketUrl + baseFolder + filename;
    }

    @Override
    public String getAvatarFileUrl() {
        return this.getFileUrl(AVATAR);
    }

    // @Async annotation ensures that the method is executed in a different thread
    /*
     * @Async
     * public S3ObjectInputStream findByName(String fileName) {
     * log.info("Downloading file with name {}", fileName);
     * return amazonS3.getObject(s3BucketName, fileName).getObjectContent();
     * }
     */

    @Override
    @Async
    public String save(final EFolder folder, final MultipartFile multipartFile) {
        if (multipartFile == null || multipartFile.isEmpty()) {
            log.error("multipart file object does not exist");
            throw new ResourceNotFoundException(EMessage.USER_NOT_FOUND);
        }
        try {
            final File file = convertMultiPartFileToFile(multipartFile);
            final String actualFileName = file.getName();

            String[] tab = actualFileName.split("\\.");
            String extention = tab[tab.length - 1];

            if (EMediaTypes.get(extention).equals(EMediaTypes.UNSUPPORTED)) {
                log.error("File extention is unknown : {}", actualFileName);
                throw new ResourceNotFoundException(EMessage.USER_NOT_FOUND);
            }

            if (!isImage(extention)) {
                log.error("File is not an image : {}", actualFileName);
                throw new ResourceNotFoundException(EMessage.USER_NOT_FOUND);
            }

            final String fileName = LocalDateTime.now() + "_" + UUID.randomUUID().toString() + "."
                    + extention.toLowerCase();

            log.info("Uploading file with name {}", fileName);

            final PutObjectRequest putObjectRequest = new PutObjectRequest(s3BucketName,
                    baseFolder + folder.label + "/" + fileName, file);
            amazonS3.putObject(putObjectRequest);
            Files.delete(file.toPath()); // Remove the file locally created in the project folder
            return folder.label + "/" + fileName;

        } catch (AmazonServiceException e) {
            log.error("Error {} occurred while uploading file", e.getLocalizedMessage());
        } catch (IOException ex) {
            log.error("Error {} occurred while deletion temporary file", ex.getLocalizedMessage());
        }
        return null;
    }

    @Override
    public boolean delete(final String fileName) {
        if (fileName == null || fileName == AVATAR)
            return false;
        try {
            log.info("Deleting file with name {}", fileName);
            final DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(s3BucketName,
                    baseFolder + fileName);
            amazonS3.deleteObject(deleteObjectRequest);
            return true;
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
        }
        return false;
    }
}
