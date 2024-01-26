package com.victoricare.api.services;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.victoricare.api.services.impl.FileService.PropKey;

public interface IFileService {

    public Map<PropKey, Object> doUpload(MultipartFile mFile, String type);

    public void doDelete(final String folder, final String fileName);

    public String doGetBase64(String folderName, String fileName);

    public String doGetUrl(String folderName, final String filename);
}
