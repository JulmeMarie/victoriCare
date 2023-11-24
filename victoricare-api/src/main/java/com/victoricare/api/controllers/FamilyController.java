package com.victoricare.api.controllers;

import java.util.List;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.victoricare.api.dtos.inputs.FamilyInputDTO;
import com.victoricare.api.dtos.inputs.PageInputDTO;
import com.victoricare.api.dtos.outputs.FamilyOutputDTO;
import com.victoricare.api.entities.Family;
import com.victoricare.api.mappers.FamilyMapper;
import com.victoricare.api.security.AuthFilter;
import com.victoricare.api.security.roles.RequireModeratorRole;
import com.victoricare.api.security.roles.RequireUserRole;
import com.victoricare.api.security.roles.RequireAdminRole;
import com.victoricare.api.services.IFamilyService;
import com.victoricare.api.validators.impl.PageValidator;
import jakarta.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequireUserRole
@RequestMapping("/families")
public class FamilyController {

    @Autowired
    private IFamilyService service;

    @RequireUserRole
    @PostMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE, consumes = {
            MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<FamilyOutputDTO> onCreate(Authentication authentication, @ModelAttribute FamilyInputDTO dto) {
        Family family = service.doCreate(AuthFilter.getOnlineUser(authentication), dto);
        return ResponseEntity.ok(FamilyMapper.toOutput(family));
    }

    @RequireUserRole
    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = {
            MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<FamilyOutputDTO> onUpdate(Authentication authentication, @ModelAttribute FamilyInputDTO dto,
            @PathVariable Integer id) {
        Family family = service.doUpdate(AuthFilter.getOnlineUser(authentication), dto, id);
        return ResponseEntity.ok(FamilyMapper.toOutput(family));
    }

    @RequireAdminRole
    @DeleteMapping(path = "/{id}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public void onDelete(Authentication authentication, @PathVariable Integer id) {
        service.doDelete(AuthFilter.getOnlineUser(authentication), id);
    }

    @RequireUserRole
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FamilyOutputDTO> onGet(Authentication authentication, @PathVariable Integer id) {
        Family family = service.doGet(AuthFilter.getOnlineUser(authentication), id);
        return ResponseEntity.ok(FamilyMapper.toOutput(family));
    }

    @RequireModeratorRole
    @GetMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<FamilyOutputDTO>> onList(@Valid PageInputDTO dto) {
        Pageable pageable = PageValidator.getPageable(Family.class, dto);
        Page<Family> page = service.doPage(pageable);
        return ResponseEntity.ok(page.map(FamilyMapper::toOutput));
    }

    @RequireUserRole
    @GetMapping(path = "/by-parent/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<FamilyOutputDTO>> onListByParent(Authentication authentication,
            @PathVariable Integer userId) {
        List<Family> list = service.doListByParent(AuthFilter.getOnlineUser(authentication), userId);
        return ResponseEntity.ok(list.stream().map(FamilyMapper::toOutput).toList());
    }
}
