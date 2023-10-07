package com.victoricare.api.services.impl;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.victoricare.api.dtos.ContentDTO;
import com.victoricare.api.dtos.SectionDTO;
import com.victoricare.api.entities.Content;
import com.victoricare.api.entities.Section;
import com.victoricare.api.entities.User;
import com.victoricare.api.enums.EContentType;
import com.victoricare.api.enums.EFolder;
import com.victoricare.api.exceptions.ResourceNotFoundException;
import com.victoricare.api.models.ContentModel;
import com.victoricare.api.repositories.ContentRepository;
import com.victoricare.api.security.jwt.JWTUtils;
import com.victoricare.api.services.IContentService;
import com.victoricare.api.services.IFileService;

@Service
public class ContentServiceImpl implements IContentService {
	private static final Logger logger = LoggerFactory.getLogger(ContentServiceImpl.class);

	@Autowired
	private ContentRepository contentRepository;

	@Autowired
	private IFileService fileService;
	
	@Autowired
	private JWTUtils jwtUtils;

	@Override
	public ContentModel create(User author, ContentDTO appRequest) {
		Date now = Date.from(Instant.now());
		Content content = new Content();
		content.setCreateAtContent(now);
		content.setCreateByContent(author);
		content.setTitleContent(appRequest.getTitle());
		content.setTypeContent(appRequest.getType());

		if (appRequest.getSections() != null && appRequest.getSections().size() > 0) {
			appRequest.getSections().forEach(sectionFromRequest -> {
				Section section = new Section();
				section.setContentSection(content);
				section.setCreateAtSection(now);
				section.setCreateBySection(author);
				section.setHtmlClassSection(sectionFromRequest.getHtmlClass());
				section.setOrderSection(sectionFromRequest.getOrder());
				section.setTextSection(sectionFromRequest.getText());
				section.setTitleSection(sectionFromRequest.getTitle());
				
				this.fileService.setS3BucketName(this.jwtUtils.getS3BucketName());
				
				if (!sectionFromRequest.getLeftMFile().isEmpty()) {
					section.setLeftImageSection(
							this.fileService.save(EFolder.get(content.getTypeContent()),
									sectionFromRequest.getLeftMFile()));
				}
				if (!sectionFromRequest.getRightMFile().isEmpty()) {
					section.setRightImageSection(
							this.fileService.save(EFolder.get(content.getTypeContent()),
									sectionFromRequest.getRightMFile()));
				}
			});
		}
		this.contentRepository.save(content);
		fileService.setS3BucketUrl(this.jwtUtils.getS3BucketUrl());
		return ContentModel.newInstance().init(fileService , content);
	}

	public boolean isSectionExistIn(Content content, SectionDTO sectionFromRequest) {
		return content.getSections().stream().anyMatch(s -> s.getIdSection().equals(sectionFromRequest.getId()));
	}

	public boolean isSectionExistIn(ContentDTO appRequest, Section section) {
		return appRequest.getSections().stream().anyMatch(s -> s.getId().equals(section.getIdSection()));
	}

	@Override
	public ContentModel update(User author, ContentDTO appRequest) {
		Date now = Date.from(Instant.now());
		Content content = this.contentRepository.findNonDeletedById(appRequest.getId()).orElseThrow(() -> {
			logger.error("content not found for id : {}", appRequest.getId());
			throw new ResourceNotFoundException();
		});

		content.setTitleContent(appRequest.getTitle());

		List<Section> refreshSections = new ArrayList<>();

		appRequest.getSections().forEach(sectionFromRequest -> {
			this.fileService.setS3BucketName(this.jwtUtils.getS3BucketName());
			Section section = new Section();
			if (!this.isSectionExistIn(content, sectionFromRequest)) { // Create Sections
				section.setContentSection(content);
				section.setCreateAtSection(now);
				section.setCreateBySection(author);
				section.setHtmlClassSection(sectionFromRequest.getHtmlClass());
				section.setOrderSection(sectionFromRequest.getOrder());
				section.setTitleSection(sectionFromRequest.getTitle());
				section.setTextSection(sectionFromRequest.getText());

				if (!sectionFromRequest.getLeftMFile().isEmpty()) {
					final String  leftImageToDelete = section.getLeftImageSection();
					section.setLeftImageSection(
							this.fileService.save(
									EFolder.get(content.getTypeContent()),
									sectionFromRequest.getLeftMFile()));
					
					this.fileService.delete(leftImageToDelete);
					
				}
				if (!sectionFromRequest.getRightMFile().isEmpty()) {
					final String rightImageToDelete = section.getRightImageSection();
					section.setRightImageSection(
							this.fileService.save(
									EFolder.get(content.getTypeContent()),
									sectionFromRequest.getRightMFile()));
					this.fileService.delete(rightImageToDelete);
				}
			} else {// Update Sections
				section = content.getSections().stream()
						.filter(s -> s.getIdSection() == sectionFromRequest.getId())
						.findFirst()
						.get();

				section.setOrderSection(sectionFromRequest.getOrder());
				section.setTitleSection(sectionFromRequest.getTitle());
				section.setTextSection(sectionFromRequest.getText());
				section.setUpdateAtSection(now);
				section.setUpdateBySection(author);

				if (!sectionFromRequest.getLeftMFile().isEmpty()) {
					final String leftImageToDelete = section.getLeftImageSection();
					section.setLeftImageSection(
							this.fileService.save(
									EFolder.get(content.getTypeContent()),
									sectionFromRequest.getLeftMFile()));
					if (leftImageToDelete != null) {
						fileService.delete(leftImageToDelete);
					}
				}
				if (!sectionFromRequest.getRightMFile().isEmpty()) {
					final String rightImageToDelete = section.getRightImageSection();
					section.setRightImageSection(
							this.fileService.save(
									EFolder.get(content.getTypeContent()),
									sectionFromRequest.getRightMFile()));
					if (rightImageToDelete != null) {
						fileService.delete(rightImageToDelete);
					}
				}
			}
			refreshSections.add(section);
		});

		content.getSections().forEach(section -> { // Delete sections
			if (!this.isSectionExistIn(appRequest, section)) {
				section.setDeleteAtSection(now);
				section.setDeleteBySection(author);
				refreshSections.add(section);
			}
		});

		content.setSections(refreshSections);
		this.contentRepository.save(content);
		fileService.setS3BucketUrl(this.jwtUtils.getS3BucketUrl());
		return ContentModel.newInstance().init(fileService ,this.removeDeletedSections(List.of(content)).get(0));
	}

	@Override
	public void delete(User author, Integer contentId) {
		this.contentRepository.findNonDeletedById(contentId).ifPresentOrElse(content -> {
			content.setDeleteAtContent(Date.from(Instant.now()));
			content.setDeleteByContent(author);
			this.contentRepository.save(content);
		}, () -> {
			logger.error("content not found for id : {}", contentId);
			throw new ResourceNotFoundException();
		});
	}

	@Override
	public ContentModel unique(Integer contentId) {
		Content content = this.contentRepository.findNonDeletedById(contentId).orElseThrow(() -> {
			logger.error("carousel item not found for id : {}", contentId);
			throw new ResourceNotFoundException();
		});
		
		fileService.setS3BucketUrl(this.jwtUtils.getS3BucketUrl());
		return ContentModel.newInstance().init(fileService ,content);
	}

	@Override
	public List<ContentModel> list(String typeStr) {

		try {
			EContentType contentType = EContentType.get(typeStr);
			if (contentType.equals(EContentType.UNKNOWN))
				return null;

			List<String> types = contentType.equals(EContentType.ALL) ? Stream.of(EContentType.values())
					.map(type -> type.name().toLowerCase())
					.collect(Collectors.toList()) : List.of(contentType.name());

			List<Content> contentList = this.contentRepository.findNonDeletedAll(types);

			contentList = this.removeDeletedSections(contentList);

			List<Content> contentListOrdered = contentList.stream().map(content -> {
				List<Section> orderedSections = content.getSections();
				orderedSections.sort((a, b) -> {
					return a.getOrderSection() - b.getOrderSection();
				});
				content.setSections(orderedSections);
				return content;
			}).collect(Collectors.toList());
			fileService.setS3BucketUrl(this.jwtUtils.getS3BucketUrl());
			return contentListOrdered
					.stream()
					.map(content -> ContentModel.newInstance().init(fileService ,content))
					.collect(Collectors.toList());
		} catch (Exception e) {
			throw new ResourceNotFoundException();
		}
	}

	public List<Content> removeDeletedSections(List<Content> contentList) {
		return contentList.stream().map(content -> {
			List<Section> nonDeletedSection = content.getSections().stream()
					.filter(s -> s.getDeleteAtSection() == null && s.getDeleteBySection() == null)
					.collect(Collectors.toList());
			content.setSections(nonDeletedSection);
			return content;
		}).collect(Collectors.toList());
	}
}