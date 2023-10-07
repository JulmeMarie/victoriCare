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

import com.victoricare.api.dtos.CarouselItemDTO;
import com.victoricare.api.models.CarouselItemModel;
import com.victoricare.api.security.jwt.AuthTokenFilter;
import com.victoricare.api.services.ICarouselItemService;

@CrossOrigin(origins = "*", maxAge = 3600)
@Validated
@RestController
public class CarouselItemController {

	@Autowired
	public ICarouselItemService carouselItemService;
	
	
	@PutMapping(path = "/api/right-mod/carousel-item", produces = MediaType.APPLICATION_JSON_VALUE, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE})
	public ResponseEntity<CarouselItemModel> create(Authentication authentication, @ModelAttribute CarouselItemDTO carouselItemReq) {
		return ResponseEntity.ok(carouselItemService.create(AuthTokenFilter.getOnlineUser(authentication), carouselItemReq));
	}

	@PostMapping(path = "/api/right-mod/carousel-item", produces = MediaType.APPLICATION_JSON_VALUE, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE})
	public ResponseEntity<CarouselItemModel> update(Authentication authentication, @ModelAttribute CarouselItemDTO carouselItemReq) {
		return ResponseEntity.ok(carouselItemService.update(AuthTokenFilter.getOnlineUser(authentication), carouselItemReq));
	}

	@DeleteMapping(path = "/api/right-mod/carousel-items/{id}")
	public void delete(Authentication authentication, @PathVariable Integer id) {
		 carouselItemService.delete(AuthTokenFilter.getOnlineUser(authentication), id);
	}

	@GetMapping(path = "/api/right-anm/carousel-items/{carouselItemId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CarouselItemModel> unique(@PathVariable Integer carouselItemId) {
		return ResponseEntity.ok(carouselItemService.unique(carouselItemId));
	}

	@GetMapping(path = "/api/right-anm/carousel-items", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<CarouselItemModel>> list() {
		return ResponseEntity.ok(carouselItemService.list());
	}
}
