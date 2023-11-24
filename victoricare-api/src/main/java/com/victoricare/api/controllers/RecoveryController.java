package com.victoricare.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.victoricare.api.dtos.inputs.PageInputDTO;
import com.victoricare.api.dtos.outputs.RecoveryOutputDTO;
import com.victoricare.api.entities.Recovery;
import com.victoricare.api.mappers.RecoveryMapper;
import com.victoricare.api.security.AuthFilter;
import com.victoricare.api.security.JWTToken;
import com.victoricare.api.security.roles.RequireAdminRole;
import com.victoricare.api.security.roles.RequireAnonymousRole;
import com.victoricare.api.security.roles.RequireModeratorRole;
import com.victoricare.api.security.roles.RequireSuperAdminRole;
import com.victoricare.api.services.IRecoveryService;
import com.victoricare.api.validators.ValidEmail;
import com.victoricare.api.validators.impl.PageValidator;

import jakarta.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/recoveries")
public class RecoveryController {

    @Autowired
    private IRecoveryService service;

    @Autowired
    private JWTToken jwtToken;

    @ValidEmail
    @RequireAnonymousRole
    @PostMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE, consumes = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<RecoveryOutputDTO> onRecover(@Valid String email) {
        jwtToken.init();
        Recovery recovery = service.doCreate(jwtToken, email);
        return ResponseEntity.ok(RecoveryMapper.toOutput(recovery));
    }

    @PutMapping(path = "/confirm/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<RecoveryOutputDTO> onValidate(@RequestBody Integer code, @PathVariable Integer id) {
        jwtToken.init();
        Recovery recovery = service.doValidate(jwtToken, id, code);
        return ResponseEntity.ok(RecoveryMapper.toOutput(recovery));
    }

    @RequireSuperAdminRole
    @DeleteMapping(path = "/{id}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public void onDelete(Authentication authentication, @PathVariable Integer id) {
        service.doDelete(AuthFilter.getOnlineUser(authentication), id);
    }

    @RequireAdminRole
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RecoveryOutputDTO> onGet(@PathVariable Integer id) {
        Recovery recovery = service.doGet(id);
        return ResponseEntity.ok(RecoveryMapper.toOutput(recovery));
    }

    @RequireModeratorRole
    @GetMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<RecoveryOutputDTO>> onList(@Valid PageInputDTO dto) {
        Pageable pageable = PageValidator.getPageable(Recovery.class, dto);
        Page<Recovery> page = service.doPage(pageable);
        return ResponseEntity.ok(page.map(RecoveryMapper::toOutput));
    }
}
