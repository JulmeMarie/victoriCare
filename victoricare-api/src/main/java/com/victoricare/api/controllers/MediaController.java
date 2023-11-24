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
import com.victoricare.api.dtos.inputs.MediaInputDTO;
import com.victoricare.api.dtos.inputs.PageInputDTO;
import com.victoricare.api.dtos.outputs.MediaOutputDTO;
import com.victoricare.api.entities.Media;
import com.victoricare.api.mappers.MediaMapper;
import com.victoricare.api.security.AuthFilter;
import com.victoricare.api.security.roles.RequireModeratorRole;
import com.victoricare.api.security.roles.RequireUserRole;
import com.victoricare.api.services.IMediaService;
import com.victoricare.api.validators.impl.PageValidator;
import com.victoricare.api.security.roles.RequireAdminRole;
import jakarta.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequireUserRole
@RequestMapping("/medias")
public class MediaController {

    @Autowired
    private IMediaService service;

    @RequireUserRole
    @PostMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE, consumes = {
            MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<MediaOutputDTO> onCreate(Authentication authentication, @ModelAttribute MediaInputDTO dto) {
        Media media = service.doCreate(AuthFilter.getOnlineUser(authentication), dto);
        return ResponseEntity.ok(MediaMapper.toOutput(media));
    }

    @RequireUserRole
    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = {
            MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<MediaOutputDTO> onUpdate(Authentication authentication, @ModelAttribute MediaInputDTO dto,
            @PathVariable Integer id) {
        Media media = service.doUpdate(AuthFilter.getOnlineUser(authentication), dto, id);
        return ResponseEntity.ok(MediaMapper.toOutput(media));
    }

    @RequireAdminRole
    @DeleteMapping(path = "/{id}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public void onDelete(Authentication authentication, @PathVariable Integer id) {
        service.doDelete(AuthFilter.getOnlineUser(authentication), id);
    }

    @RequireUserRole
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MediaOutputDTO> onGet(Authentication authentication, @PathVariable Integer id) {
        Media media = service.doGet(AuthFilter.getOnlineUser(authentication), id);
        return ResponseEntity.ok(MediaMapper.toOutput(media));
    }

    @RequireModeratorRole
    @GetMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<MediaOutputDTO>> onList(@Valid PageInputDTO dto) {
        Pageable pageable = PageValidator.getPageable(Media.class, dto);
        Page<Media> page = service.doPage(pageable);
        return ResponseEntity.ok(page.map(MediaMapper::toOutput));
    }

    @RequireUserRole
    @GetMapping(path = "/by-section/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MediaOutputDTO>> onListBySection(Authentication authentication,
            @PathVariable Integer id) {
        List<Media> list = service.doListBySection(AuthFilter.getOnlineUser(authentication), id);
        return ResponseEntity.ok(list.stream().map(MediaMapper::toOutput).toList());
    }
}
