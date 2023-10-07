package com.victoricare.api.services.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.amazonaws.services.s3.AmazonS3;
import com.victoricare.api.enums.EFolder;
import com.victoricare.api.enums.EMediaTypes;
import com.victoricare.api.exceptions.ResourceNotCreatedException;
import com.victoricare.api.security.jwt.JWTUtils;
import com.victoricare.api.services.IFileService;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import org.springframework.scheduling.annotation.Async;
import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Service
public class FileServiceImpl implements IFileService {
    private static final Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);
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
            logger.error("Error {} occurred while converting the multipart file", e.getLocalizedMessage());
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
     * logger.info("Downloading file with name {}", fileName);
     * return amazonS3.getObject(s3BucketName, fileName).getObjectContent();
     * }
     */

    @Override
    @Async
    public String save(final EFolder folder, final MultipartFile multipartFile) {
    	if(multipartFile == null || multipartFile.isEmpty()) {
    		logger.error("multipart file object does not exist");
    		throw new ResourceNotCreatedException();
    	}
        try {
            final File file = convertMultiPartFileToFile(multipartFile);
            final String actualFileName = file.getName();

            String[] tab = actualFileName.split("\\.");
            String extention = tab[tab.length - 1];

            if (EMediaTypes.get(extention).equals(EMediaTypes.UNSUPPORTED)) {
                logger.error("File extention is unknown : {}", actualFileName);
                throw new ResourceNotCreatedException();
            }

            if (!isImage(extention)) {
                logger.error("File is not an image : {}", actualFileName);
                throw new ResourceNotCreatedException();
            }

            final String fileName = LocalDateTime.now() + "_" + UUID.randomUUID().toString() + "."
                    + extention.toLowerCase();

            logger.info("Uploading file with name {}", fileName);

            final PutObjectRequest putObjectRequest = new PutObjectRequest(s3BucketName,
                    baseFolder + folder.label + "/" + fileName, file);
            amazonS3.putObject(putObjectRequest);
            Files.delete(file.toPath()); // Remove the file locally created in the project folder
            return folder.label + "/" + fileName;

        } catch (AmazonServiceException e) {
            logger.error("Error {} occurred while uploading file", e.getLocalizedMessage());
        } catch (IOException ex) {
            logger.error("Error {} occurred while deleting temporary file", ex.getLocalizedMessage());
        }
        return null;
    }

    @Override
    public boolean delete(final String fileName) {
    	if(fileName == null || fileName == AVATAR) return false;
        try {
        	logger.info("Deleting file with name {}", fileName);
            final DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(s3BucketName,
                    baseFolder + fileName);
            amazonS3.deleteObject(deleteObjectRequest);
            return true;
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
        }
        return false;
    }
}
