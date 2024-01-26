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
import com.victoricare.api.dtos.inputs.BabySitterInputDTO;
import com.victoricare.api.dtos.inputs.PageInputDTO;
import com.victoricare.api.dtos.outputs.AddressOutputDTO;
import com.victoricare.api.dtos.outputs.BabySitterOutputDTO;
import com.victoricare.api.entities.Address;
import com.victoricare.api.entities.BabySitter;
import com.victoricare.api.mappers.AddressMapper;
import com.victoricare.api.mappers.BabySitterMapper;
import com.victoricare.api.security.AuthFilter;
import com.victoricare.api.security.roles.RequireAdminRole;
import com.victoricare.api.security.roles.RequireModeratorRole;
import com.victoricare.api.security.roles.RequireUserRole;
import com.victoricare.api.services.IBabySitterService;
import com.victoricare.api.validators.impl.PageValidator;
import jakarta.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/baby-sitters")
@RequireUserRole
public class BabySitterController {

    @Autowired
    private IBabySitterService service;

    @RequireUserRole
    @PostMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE, consumes = {
            MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<BabySitterOutputDTO> onCreate(Authentication authentication,
            @ModelAttribute BabySitterInputDTO dto) {
        BabySitter babySitter = service.doCreate(AuthFilter.getOnlineUser(authentication), dto);
        return ResponseEntity.ok(BabySitterMapper.toOutput(babySitter));
    }

    @RequireUserRole
    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = {
            MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<BabySitterOutputDTO> onUpdate(Authentication authentication,
            @ModelAttribute BabySitterInputDTO dto,
            @PathVariable Long id) {
        BabySitter babySitter = service.doUpdate(AuthFilter.getOnlineUser(authentication), dto, id);
        return ResponseEntity.ok(BabySitterMapper.toOutput(babySitter));
    }

    @RequireAdminRole
    @DeleteMapping(path = "/{id}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public void onDelete(Authentication authentication,
            @PathVariable Long id) {
        service.doDelete(AuthFilter.getOnlineUser(authentication), id);
    }

    @RequireUserRole
    @DeleteMapping(path = "/cancel/{id}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public void onCancel(Authentication authentication, @PathVariable Long id) {
        service.doCancel(AuthFilter.getOnlineUser(authentication), id);
    }

    @RequireUserRole
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BabySitterOutputDTO> onGet(Authentication authentication, @PathVariable Long id) {
        BabySitter babySitter = service.doGet(AuthFilter.getOnlineUser(authentication).getId(), id);
        return ResponseEntity.ok(BabySitterMapper.toOutput(babySitter));
    }

    @RequireModeratorRole
    @GetMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<BabySitterOutputDTO>> onList(@Valid PageInputDTO dto) {
        Pageable pageable = PageValidator.getPageable(BabySitter.class, dto);
        Page<BabySitter> page = service.doPage(pageable);
        return ResponseEntity.ok(page.map(BabySitterMapper::toOutput));
    }

    @RequireUserRole
    @GetMapping(path = "/by-family/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BabySitterOutputDTO>> onListByFamily(Authentication authentication,
            @PathVariable Integer id) {
        List<BabySitter> babySitterList = service.doListByFamily(AuthFilter.getOnlineUser(authentication), id);
        return ResponseEntity.ok(babySitterList.stream().map(BabySitterMapper::toOutput).toList());
    }

    @RequireUserRole
    @GetMapping(path = "/by-user/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BabySitterOutputDTO>> onListByUser(Authentication authentication,
            @PathVariable Integer id) {
        List<BabySitter> babySitterList = service.doListByUser(AuthFilter.getOnlineUser(authentication), id);
        return ResponseEntity.ok(babySitterList.stream().map(BabySitterMapper::toOutput).toList());
    }
}
