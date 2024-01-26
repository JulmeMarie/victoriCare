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
import com.victoricare.api.dtos.inputs.ChildInputDTO;
import com.victoricare.api.dtos.inputs.PageInputDTO;
import com.victoricare.api.dtos.outputs.AddressOutputDTO;
import com.victoricare.api.dtos.outputs.ChildOutputDTO;
import com.victoricare.api.entities.Child;
import com.victoricare.api.mappers.AddressMapper;
import com.victoricare.api.mappers.ChildMapper;
import com.victoricare.api.security.AuthFilter;
import com.victoricare.api.security.roles.RequireAdminRole;
import com.victoricare.api.security.roles.RequireModeratorRole;
import com.victoricare.api.security.roles.RequireUserRole;
import com.victoricare.api.services.IChildService;
import com.victoricare.api.validators.impl.PageValidator;
import jakarta.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequireUserRole
@RequestMapping("/children")
public class ChildController {

    @Autowired
    private IChildService service;

    @RequireUserRole
    @PostMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE, consumes = {
            MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<ChildOutputDTO> onCreate(Authentication authentication, @ModelAttribute ChildInputDTO dto) {
        Child child = service.doCreate(AuthFilter.getOnlineUser(authentication), dto);
        return ResponseEntity.ok(ChildMapper.toOutput(child));
    }

    @RequireUserRole
    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = {
            MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<ChildOutputDTO> onUpdate(Authentication authentication, @ModelAttribute ChildInputDTO dto,
            @PathVariable Integer id) {
        Child child = service.doUpdate(AuthFilter.getOnlineUser(authentication), dto, id);
        return ResponseEntity.ok(ChildMapper.toOutput(child));
    }

    @RequireUserRole
    @DeleteMapping(path = "/{id}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public void onDelete(Authentication authentication, @PathVariable Integer id) {
        service.doDelete(AuthFilter.getOnlineUser(authentication), id);
    }

    @RequireUserRole
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ChildOutputDTO> onGet(Authentication authentication, @PathVariable Integer id) {
        Child child = service.doGet(AuthFilter.getOnlineUser(authentication), id);
        return ResponseEntity.ok(ChildMapper.toOutput(child));
    }

    @RequireModeratorRole
    @GetMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<ChildOutputDTO>> onList(@Valid PageInputDTO dto) {
        Pageable pageable = PageValidator.getPageable(Child.class, dto);
        Page<Child> page = service.doPage(pageable);
        return ResponseEntity.ok(page.map(ChildMapper::toOutput));
    }

    @RequireUserRole
    @GetMapping(path = "/by-family/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ChildOutputDTO>> onListByFamily(Authentication authentication,
            @PathVariable Integer id) {
        List<Child> children = service.doListByFamily(AuthFilter.getOnlineUser(authentication), id);
        return ResponseEntity.ok(children.stream().map(ChildMapper::toOutput).toList());
    }
}