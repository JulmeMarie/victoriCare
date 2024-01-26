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
import com.victoricare.api.dtos.inputs.TaskInputDTO;
import com.victoricare.api.dtos.outputs.TaskOutputDTO;
import com.victoricare.api.entities.Task;
import com.victoricare.api.mappers.TaskMapper;
import com.victoricare.api.security.AuthFilter;
import com.victoricare.api.security.roles.RequireModeratorRole;
import com.victoricare.api.security.roles.RequireUserRole;
import com.victoricare.api.security.roles.RequireAdminRole;
import com.victoricare.api.services.ITaskService;
import com.victoricare.api.validators.impl.PageValidator;

import jakarta.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequireUserRole
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private ITaskService service;

    @RequireUserRole
    @PostMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE, consumes = {
            MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<TaskOutputDTO> onCreate(Authentication authentication,
            @ModelAttribute TaskInputDTO dto) {
        Task task = service.doCreate(AuthFilter.getOnlineUser(authentication), dto);
        return ResponseEntity.ok(TaskMapper.toOutput(task));
    }

    @RequireUserRole
    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = {
            MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<TaskOutputDTO> onUpdate(Authentication authentication, @ModelAttribute TaskInputDTO dto,
            @PathVariable Long id) {
        Task task = service.doUpdate(AuthFilter.getOnlineUser(authentication), dto, id);
        return ResponseEntity.ok(TaskMapper.toOutput(task));
    }

    @RequireAdminRole
    @DeleteMapping(path = "/{id}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public void onDelete(Authentication authentication, @PathVariable Long id) {
        service.doDelete(AuthFilter.getOnlineUser(authentication), id);
    }

    @RequireUserRole
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TaskOutputDTO> onGet(Authentication authentication, @PathVariable Long id) {
        Task task = service.doGet(AuthFilter.getOnlineUser(authentication), id);
        return ResponseEntity.ok(TaskMapper.toOutput(task));
    }

    @RequireModeratorRole
    @GetMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<TaskOutputDTO>> onList(@Valid PageInputDTO dto) {
        Pageable pageable = PageValidator.getPageable(Task.class, dto);
        Page<Task> page = service.doPage(pageable);
        return ResponseEntity.ok(page.map(TaskMapper::toOutput));
    }

    @RequireUserRole
    @GetMapping(path = "/by-user-id", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TaskOutputDTO>> onListByUser(Authentication authentication,
            @PathVariable Integer userId) {
        List<Task> list = service.doListByUser(AuthFilter.getOnlineUser(authentication), userId);
        return ResponseEntity.ok(list.stream().map(TaskMapper::toOutput).toList());
    }
}
