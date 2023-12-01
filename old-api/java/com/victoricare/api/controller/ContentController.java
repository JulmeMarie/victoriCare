package com.victoricare.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.victoricare.api.dtos.ContentDTO;
import com.victoricare.api.models.ContentModel;
import com.victoricare.api.security.jwt.AuthTokenFilter;
import com.victoricare.api.services.IContentService;

@CrossOrigin(origins = "*", maxAge = 3600)
@Validated
@RestController
public class ContentController {

	@Autowired
	public IContentService contentService;

	@PutMapping(path = "/api/right-mod/content", produces = MediaType.APPLICATION_JSON_VALUE, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE})
	public ResponseEntity<ContentModel> create(Authentication authentication, @ModelAttribute ContentDTO contentReq) {
		return ResponseEntity.ok(contentService.create(AuthTokenFilter.getOnlineUser(authentication), contentReq));
	}

	@PostMapping(path = "/api/right-mod/content", produces = MediaType.APPLICATION_JSON_VALUE, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE})
	public ResponseEntity<ContentModel> update(Authentication authentication, @ModelAttribute ContentDTO contentReq) {
		return ResponseEntity.ok(contentService.update(AuthTokenFilter.getOnlineUser(authentication), contentReq));
	}

	@DeleteMapping(path = "/api/right-mod/contents/{contentId}")
	public void delete(Authentication authentication, @PathVariable Integer contentId) {
		contentService.delete(AuthTokenFilter.getOnlineUser(authentication), contentId);
	}

	@GetMapping(path = "/api/right-anm/contents/{contentId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ContentModel> unique(@PathVariable Integer contentId) {
		return ResponseEntity.ok(contentService.unique(contentId));
	}

	@GetMapping(path = "/api/right-anm/contents-by-type/{type}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ContentModel>> list(@PathVariable String type) {
		return ResponseEntity.ok(contentService.list(type));
	}
}
