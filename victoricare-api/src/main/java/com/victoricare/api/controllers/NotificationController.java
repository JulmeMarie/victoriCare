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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.victoricare.api.dtos.inputs.NotificationInputDTO;
import com.victoricare.api.dtos.inputs.PageInputDTO;
import com.victoricare.api.dtos.outputs.NotificationOutputDTO;
import com.victoricare.api.entities.Notification;
import com.victoricare.api.mappers.NotificationMapper;
import com.victoricare.api.security.AuthFilter;
import com.victoricare.api.security.roles.RequireModeratorRole;
import com.victoricare.api.security.roles.RequireUserRole;
import com.victoricare.api.security.roles.RequireAdminRole;
import com.victoricare.api.services.INotificationService;
import com.victoricare.api.validators.impl.PageValidator;

import jakarta.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequireUserRole
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private INotificationService service;

    @RequireUserRole
    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = {
            MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<NotificationOutputDTO> onUpdate(Authentication authentication,
            @ModelAttribute NotificationInputDTO dto,
            @PathVariable Long id) {
        Notification notification = service.doView(AuthFilter.getOnlineUser(authentication), id);
        return ResponseEntity.ok(NotificationMapper.toOutput(notification));
    }

    @RequireAdminRole
    @DeleteMapping(path = "/{id}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public void onDelete(Authentication authentication, @PathVariable Long id) {
        service.doDelete(AuthFilter.getOnlineUser(authentication), id);
    }

    @RequireUserRole
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NotificationOutputDTO> onGet(Authentication authentication, @PathVariable Long id) {
        Notification notification = service.doGet(AuthFilter.getOnlineUser(authentication), id);
        return ResponseEntity.ok(NotificationMapper.toOutput(notification));
    }

    @RequireModeratorRole
    @GetMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<NotificationOutputDTO>> onList(@Valid PageInputDTO dto) {
        Pageable pageable = PageValidator.getPageable(Notification.class, dto);
        Page<Notification> page = service.doPage(pageable);
        return ResponseEntity.ok(page.map(NotificationMapper::toOutput));
    }

    @RequireUserRole
    @GetMapping(path = "/by-user/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<NotificationOutputDTO>> onListByUser(Authentication authentication,
            @PathVariable Integer id) {
        List<Notification> list = service.doListByUser(AuthFilter.getOnlineUser(authentication), id);
        return ResponseEntity.ok(list.stream().map(NotificationMapper::toOutput).toList());
    }

    @RequireUserRole
    @GetMapping(path = "/by-baby-sitter/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<NotificationOutputDTO>> onListByBabySitter(Authentication authentication,
            @PathVariable Long id) {
        List<Notification> list = service.doListByBabySitter(AuthFilter.getOnlineUser(authentication), id);
        return ResponseEntity.ok(list.stream().map(NotificationMapper::toOutput).toList());
    }
}
