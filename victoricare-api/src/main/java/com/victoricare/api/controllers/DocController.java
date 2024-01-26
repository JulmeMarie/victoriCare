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
import com.victoricare.api.dtos.inputs.DocInputDTO;
import com.victoricare.api.dtos.inputs.PageInputDTO;
import com.victoricare.api.dtos.outputs.AddressOutputDTO;
import com.victoricare.api.dtos.outputs.DocOutputDTO;
import com.victoricare.api.entities.Doc;
import com.victoricare.api.mappers.AddressMapper;
import com.victoricare.api.mappers.DocMapper;
import com.victoricare.api.security.AuthFilter;
import com.victoricare.api.security.roles.RequireAdminRole;
import com.victoricare.api.security.roles.RequireModeratorRole;
import com.victoricare.api.security.roles.RequireUserRole;
import com.victoricare.api.services.IDocService;
import com.victoricare.api.validators.impl.PageValidator;
import jakarta.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequireUserRole
@RequestMapping("/docs")
public class DocController {

    @Autowired
    private IDocService service;

    @RequireUserRole
    @PostMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE, consumes = {
            MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<DocOutputDTO> onCreate(Authentication authentication, @ModelAttribute DocInputDTO dto) {
        Doc doc = service.doCreate(AuthFilter.getOnlineUser(authentication), dto);
        return ResponseEntity.ok(DocMapper.toOutput(doc));
    }

    @RequireUserRole
    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = {
            MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<DocOutputDTO> onUpdate(Authentication authentication, @ModelAttribute DocInputDTO dto,
            @PathVariable Integer id) {
        Doc doc = service.doUpdate(AuthFilter.getOnlineUser(authentication), dto, id);
        return ResponseEntity.ok(DocMapper.toOutput(doc));
    }

    @RequireUserRole
    @DeleteMapping(path = "/{id}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public void onDelete(Authentication authentication, @PathVariable Integer id) {
        service.doDelete(AuthFilter.getOnlineUser(authentication), id);
    }

    @RequireUserRole
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DocOutputDTO> onGet(Authentication authentication, @PathVariable Integer id) {
        Doc doc = service.doGet(AuthFilter.getOnlineUser(authentication), id);
        return ResponseEntity.ok(DocMapper.toOutput(doc));
    }

    @RequireModeratorRole
    @GetMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<DocOutputDTO>> onList(@Valid PageInputDTO dto) {
        Pageable pageable = PageValidator.getPageable(Doc.class, dto);
        Page<Doc> page = service.doPage(pageable);
        return ResponseEntity.ok(page.map(DocMapper::toOutput));
    }

    @RequireUserRole
    @GetMapping(path = "/by-user/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<DocOutputDTO>> onListByUser(Authentication authentication,
            @PathVariable Integer id) {
        List<Doc> list = service.doListByUser(AuthFilter.getOnlineUser(authentication), id);
        return ResponseEntity.ok(list.stream().map(DocMapper::toOutput).toList());
    }
}
