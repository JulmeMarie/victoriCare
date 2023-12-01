package com.victoricare.api.services;

import java.util.List;

import com.victoricare.api.dtos.ContentDTO;
import com.victoricare.api.entities.User;
import com.victoricare.api.models.ContentModel;

public interface IContentService {

	public ContentModel create(User author, ContentDTO contentRequest);

	public ContentModel update(User author, ContentDTO contentRequest);

	public void delete(User author, Integer contentId);

	public ContentModel unique(Integer contentId);

	public List<ContentModel> list(String typeStr);

	// FileResponse loadImage(String folder, String fileName);
}
