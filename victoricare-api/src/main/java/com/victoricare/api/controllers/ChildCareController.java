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
import com.victoricare.api.dtos.inputs.ChildCareInputDTO;
import com.victoricare.api.dtos.inputs.PageInputDTO;
import com.victoricare.api.dtos.outputs.AddressOutputDTO;
import com.victoricare.api.dtos.outputs.ChildCareOutputDTO;
import com.victoricare.api.entities.ChildCare;
import com.victoricare.api.mappers.AddressMapper;
import com.victoricare.api.mappers.ChildCareMapper;
import com.victoricare.api.security.AuthFilter;
import com.victoricare.api.security.roles.RequireAdminRole;
import com.victoricare.api.security.roles.RequireModeratorRole;
import com.victoricare.api.security.roles.RequireUserRole;
import com.victoricare.api.services.IChildCareService;
import com.victoricare.api.validators.impl.PageValidator;
import jakarta.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequireUserRole
@RequestMapping("/child-cares")
public class ChildCareController {

    @Autowired
    private IChildCareService service;

    @RequireUserRole
    @PostMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE, consumes = {
            MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<ChildCareOutputDTO> onCreate(Authentication authentication,
            @ModelAttribute ChildCareInputDTO dto) {
        ChildCare childCare = service.doCreate(AuthFilter.getOnlineUser(authentication), dto);
        return ResponseEntity.ok(ChildCareMapper.toOutput(childCare));
    }

    @RequireUserRole
    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = {
            MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<ChildCareOutputDTO> onUpdate(Authentication authentication,
            @ModelAttribute ChildCareInputDTO dto,
            @PathVariable Long id) {
        ChildCare childCare = service.doUpdate(AuthFilter.getOnlineUser(authentication), dto, id);
        return ResponseEntity.ok(ChildCareMapper.toOutput(childCare));
    }

    @RequireUserRole
    @DeleteMapping(path = "/cancel/{id}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public void onCancel(Authentication authentication, @PathVariable Long id) {
        service.doCancel(AuthFilter.getOnlineUser(authentication), id);
    }

    @RequireAdminRole
    @DeleteMapping(path = "/{id}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public void onDelete(Authentication authentication,
            @PathVariable Long id) {
        service.doDelete(AuthFilter.getOnlineUser(authentication), id);
    }

    @RequireUserRole
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ChildCareOutputDTO> onGet(Authentication authentication, @PathVariable Long id) {
        ChildCare childCare = service.doGet(AuthFilter.getOnlineUser(authentication), id);
        return ResponseEntity.ok(ChildCareMapper.toOutput(childCare));
    }

    @RequireUserRole
    @GetMapping(path = "/by-family/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ChildCareOutputDTO>> onListByFamily(Authentication authentication,
            @PathVariable Integer id) {
        List<ChildCare> childCareList = service.doListByFamily(AuthFilter.getOnlineUser(authentication), id);
        return ResponseEntity.ok(childCareList.stream().map(ChildCareMapper::toOutput).toList());
    }

    @RequireModeratorRole
    @GetMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<ChildCareOutputDTO>> onList(@Valid PageInputDTO dto) {
        Pageable pageable = PageValidator.getPageable(ChildCare.class, dto);
        Page<ChildCare> page = service.doPage(pageable);
        return ResponseEntity.ok(page.map(ChildCareMapper::toOutput));
    }
}
