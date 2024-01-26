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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.victoricare.api.dtos.inputs.PageInputDTO;
import com.victoricare.api.dtos.outputs.BanishmentOutputDTO;
import com.victoricare.api.entities.Banishment;
import com.victoricare.api.mappers.BanishmentMapper;
import com.victoricare.api.security.AuthFilter;
import com.victoricare.api.security.JWTToken;
import com.victoricare.api.security.roles.RequireModeratorRole;
import com.victoricare.api.security.roles.RequireUserRole;
import com.victoricare.api.services.IBanishmentService;
import com.victoricare.api.validators.impl.PageValidator;

import jakarta.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/banishmentes")
@RequireUserRole
public class BanishmentController {

    @Autowired
    private IBanishmentService service;

    @Autowired
    private JWTToken jwtToken;

    @RequireUserRole
    @PostMapping(path = "/", consumes = { MediaType.APPLICATION_JSON_VALUE })
    public void onCreate() {
        service.doCreate(jwtToken);
    }

    @RequireUserRole
    @DeleteMapping(path = "/{id}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public void onDelete(Authentication authentication,
            @PathVariable Integer id) {
        service.doDelete(AuthFilter.getOnlineUser(authentication), id);
    }

    @RequireUserRole
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BanishmentOutputDTO> onGet(@PathVariable Integer id) {
        Banishment banishment = service.doGet(id);
        return ResponseEntity.ok(BanishmentMapper.toOutput(banishment));
    }

    @RequireModeratorRole
    @GetMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<BanishmentOutputDTO>> onList(@Valid PageInputDTO dto) {
        Pageable pageable = PageValidator.getPageable(Banishment.class, dto);
        Page<Banishment> page = service.doPage(pageable);
        return ResponseEntity.ok(page.map(BanishmentMapper::toOutput));
    }
}
