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
import com.victoricare.api.dtos.inputs.PageInputDTO;
import com.victoricare.api.dtos.inputs.SectionInputDTO;
import com.victoricare.api.dtos.outputs.SectionOutputDTO;
import com.victoricare.api.entities.Section;
import com.victoricare.api.mappers.SectionMapper;
import com.victoricare.api.security.AuthFilter;
import com.victoricare.api.security.roles.RequireModeratorRole;
import com.victoricare.api.security.roles.RequireUserRole;
import com.victoricare.api.security.roles.RequireAdminRole;
import com.victoricare.api.services.ISectionService;
import com.victoricare.api.validators.impl.PageValidator;

import jakarta.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequireUserRole
@RequestMapping("/sections")
public class SectionController {

    @Autowired
    private ISectionService service;

    @RequireUserRole
    @PostMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE, consumes = {
            MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<SectionOutputDTO> onCreate(Authentication authentication,
            @ModelAttribute SectionInputDTO dto) {
        Section section = service.doCreate(AuthFilter.getOnlineUser(authentication), dto);
        return ResponseEntity.ok(SectionMapper.toOutput(section));
    }

    @RequireUserRole
    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = {
            MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<SectionOutputDTO> onUpdate(Authentication authentication, @ModelAttribute SectionInputDTO dto,
            @PathVariable Integer id) {
        Section section = service.doUpdate(AuthFilter.getOnlineUser(authentication), dto, id);
        return ResponseEntity.ok(SectionMapper.toOutput(section));
    }

    @RequireAdminRole
    @DeleteMapping(path = "/{id}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public void onDelete(Authentication authentication, @PathVariable Integer id) {
        service.doDelete(AuthFilter.getOnlineUser(authentication), id);
    }

    @RequireUserRole
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SectionOutputDTO> onGet(Authentication authentication, @PathVariable Integer id) {
        Section section = service.doGet(AuthFilter.getOnlineUser(authentication), id);
        return ResponseEntity.ok(SectionMapper.toOutput(section));
    }

    @RequireModeratorRole
    @GetMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<SectionOutputDTO>> onList(@Valid PageInputDTO dto) {
        Pageable pageable = PageValidator.getPageable(Section.class, dto);
        Page<Section> page = service.doPage(pageable);
        return ResponseEntity.ok(page.map(SectionMapper::toOutput));
    }

    @RequireUserRole
    @GetMapping(path = "/by-doc/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SectionOutputDTO>> onListByDoc(Authentication authentication,
            @PathVariable Integer id) {
        List<Section> list = service.doListByDoc(AuthFilter.getOnlineUser(authentication), id);
        return ResponseEntity.ok(list.stream().map(SectionMapper::toOutput).toList());
    }
}
