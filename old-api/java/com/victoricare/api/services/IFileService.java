package com.victoricare.api.services;

import org.springframework.web.multipart.MultipartFile;

import com.victoricare.api.enums.EFolder;
public interface IFileService {
	public String save(final EFolder folder, final MultipartFile mFile);
	public boolean delete(final String filename);
	public String getFileUrl(final String filename);
	public String getAvatarFileUrl();
	public void setS3BucketName(final String s3bucketName);
	public void setS3BucketUrl(final String s3bucketUrl);
}
