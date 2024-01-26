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
import com.victoricare.api.dtos.inputs.ChildCareTaskInputDTO;
import com.victoricare.api.dtos.inputs.PageInputDTO;
import com.victoricare.api.dtos.outputs.ChildCareTaskOutputDTO;
import com.victoricare.api.entities.ChildCareTask;
import com.victoricare.api.mappers.ChildCareTaskMapper;
import com.victoricare.api.security.AuthFilter;
import com.victoricare.api.security.roles.RequireAdminRole;
import com.victoricare.api.security.roles.RequireModeratorRole;
import com.victoricare.api.security.roles.RequireUserRole;
import com.victoricare.api.services.IChildCareTaskService;
import com.victoricare.api.validators.impl.PageValidator;
import jakarta.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequireUserRole
@RequestMapping("/child-care-tasks")
public class ChildCareTaskController {

    @Autowired
    private IChildCareTaskService service;

    @RequireUserRole
    @PostMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE, consumes = {
            MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<ChildCareTaskOutputDTO> onCreate(Authentication authentication,
            @ModelAttribute ChildCareTaskInputDTO dto) {
        ChildCareTask childCare = service.doCreate(AuthFilter.getOnlineUser(authentication), dto);
        return ResponseEntity.ok(ChildCareTaskMapper.toOutput(childCare));
    }

    @RequireUserRole
    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = {
            MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<ChildCareTaskOutputDTO> onUpdate(Authentication authentication,
            @ModelAttribute ChildCareTaskInputDTO dto,
            @PathVariable Long id) {
        ChildCareTask childCare = service.doUpdate(AuthFilter.getOnlineUser(authentication), dto, id);
        return ResponseEntity.ok(ChildCareTaskMapper.toOutput(childCare));
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
    public void onDelete(Authentication authentication, @PathVariable Long id) {
        service.doDelete(AuthFilter.getOnlineUser(authentication), id);
    }

    @RequireUserRole
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ChildCareTaskOutputDTO> onGet(Authentication authentication, @PathVariable Long id) {
        ChildCareTask childCare = service.doGet(AuthFilter.getOnlineUser(authentication), id);
        return ResponseEntity.ok(ChildCareTaskMapper.toOutput(childCare));
    }

    @RequireUserRole
    @GetMapping(path = "/by-child-care/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ChildCareTaskOutputDTO>> onListByChildCare(Authentication authentication,
            @PathVariable Long id) {
        List<ChildCareTask> childCareList = service.doListByChildCare(AuthFilter.getOnlineUser(authentication), id);
        return ResponseEntity.ok(childCareList.stream().map(ChildCareTaskMapper::toOutput).toList());
    }

    @RequireModeratorRole
    @GetMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<ChildCareTaskOutputDTO>> onList(@Valid PageInputDTO dto) {
        Pageable pageable = PageValidator.getPageable(ChildCareTask.class, dto);
        Page<ChildCareTask> page = service.doPage(pageable);
        return ResponseEntity.ok(page.map(ChildCareTaskMapper::toOutput));
    }
}