package com.victoricare.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.victoricare.api.dtos.FmcValuesDTO;
import com.victoricare.api.models.FmcValuesModel;
import com.victoricare.api.security.jwt.AuthTokenFilter;
import com.victoricare.api.services.IFmcValuesService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class FmcValuesController {

	@Autowired
	public IFmcValuesService fmcValuesService;

	@PutMapping(path = "/api/right-mod/fmc-values", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<FmcValuesModel> create(Authentication authentication, @RequestBody FmcValuesDTO fmcValuesReq) {
		return ResponseEntity.ok(fmcValuesService.create(AuthTokenFilter.getOnlineUser(authentication),fmcValuesReq));
	}

	@PostMapping(path = "/api/right-mod/fmc-values", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<FmcValuesModel> update(Authentication authentication, @RequestBody FmcValuesDTO fmcValuesReq) {
		return ResponseEntity.ok(fmcValuesService.update(AuthTokenFilter.getOnlineUser(authentication),fmcValuesReq));
	}

	@DeleteMapping(path = "/api/right-mod/fmc-values/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public void delete(Authentication authentication, @PathVariable Integer id) {
		fmcValuesService.delete(AuthTokenFilter.getOnlineUser(authentication),id);
	}

	@GetMapping(path = "/api/right-anm/fmc-values/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<FmcValuesModel> unique(@PathVariable Integer id) {
		return ResponseEntity.ok(fmcValuesService.unique(id));
	}

	@GetMapping(path = "/api/right-anm/fmc-values", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<FmcValuesModel>> list() {
		return ResponseEntity.ok(fmcValuesService.list());
	}
}
