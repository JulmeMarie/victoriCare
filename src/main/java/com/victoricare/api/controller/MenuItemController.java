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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.victoricare.api.dtos.MenuItemDTO;
import com.victoricare.api.models.MenuItemModel;
import com.victoricare.api.security.jwt.AuthTokenFilter;
import com.victoricare.api.services.IMenuItemService;

@CrossOrigin(origins = "*", maxAge = 3600)
@Validated
@RestController
public class MenuItemController {

	@Autowired
	public IMenuItemService menuItemService;

	@PutMapping(path = "/api/right-mod/menu-items", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<MenuItemModel> create(Authentication authentication, @ModelAttribute MenuItemDTO menuItemReq) {
		return ResponseEntity.ok(menuItemService.create(AuthTokenFilter.getOnlineUser(authentication), menuItemReq));
	}

	@PostMapping(path = "/api/right-mod/menu-items", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MenuItemModel> update(Authentication authentication,@RequestBody MenuItemDTO menuItemReq) {
		return ResponseEntity.ok(menuItemService.update(AuthTokenFilter.getOnlineUser(authentication), menuItemReq));
	}

	@DeleteMapping(path = "/api/right-mod/menu-items/{menuItemId}")
	public void delete(Authentication authentication, @PathVariable Integer menuItemId) {
		menuItemService.delete(AuthTokenFilter.getOnlineUser(authentication), menuItemId);
	}

	@GetMapping(path = "/api/right-anm/menu-items/{menuItemId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MenuItemModel> unique(@PathVariable Integer menuItemId) {
		return ResponseEntity.ok(menuItemService.unique(menuItemId));
	}

	@GetMapping(path = "/api/right-anm/menu-items", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<MenuItemModel>> list() {
		return ResponseEntity.ok(menuItemService.list());
	}
}
