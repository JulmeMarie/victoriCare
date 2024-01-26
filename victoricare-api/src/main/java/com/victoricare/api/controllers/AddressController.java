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
import com.victoricare.api.dtos.inputs.AddressInputDTO;
import com.victoricare.api.dtos.inputs.PageInputDTO;
import com.victoricare.api.dtos.outputs.AddressOutputDTO;
import com.victoricare.api.entities.Address;
import com.victoricare.api.mappers.AddressMapper;
import com.victoricare.api.security.AuthFilter;
import com.victoricare.api.security.roles.RequireAdminRole;
import com.victoricare.api.security.roles.RequireModeratorRole;
import com.victoricare.api.security.roles.RequireUserRole;
import com.victoricare.api.services.IAddressService;
import com.victoricare.api.validators.impl.PageValidator;
import jakarta.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/addresses")
@RequireUserRole
public class AddressController {

    @Autowired
    private IAddressService service;

    @RequireUserRole
    @PostMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE, consumes = {
            MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<AddressOutputDTO> onCreate(Authentication authentication,
            @ModelAttribute AddressInputDTO dto) {
        Address address = service.doCreate(AuthFilter.getOnlineUser(authentication).getId(), dto);
        return ResponseEntity.ok(AddressMapper.toOutput(address));
    }

    @RequireUserRole
    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = {
            MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<AddressOutputDTO> onUpdate(Authentication authentication, @ModelAttribute AddressInputDTO dto,
            @PathVariable Integer id) {
        Address address = service.doUpdate(AuthFilter.getOnlineUser(authentication), dto, id);
        return ResponseEntity.ok(AddressMapper.toOutput(address));
    }

    @RequireAdminRole
    @DeleteMapping(path = "/cancel/{id}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public void onCancel(Authentication authentication, @PathVariable Integer id) {
        service.doCancel(AuthFilter.getOnlineUser(authentication), id);
    }

    @RequireUserRole
    @DeleteMapping(path = "/{id}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public void onDelete(Authentication authentication, @PathVariable Integer id) {
        service.doDelete(AuthFilter.getOnlineUser(authentication), id);
    }

    @RequireUserRole
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AddressOutputDTO> onGet(Authentication authentication, @PathVariable Integer id) {
        Address address = service.doGet(AuthFilter.getOnlineUser(authentication).getId(), id);
        return ResponseEntity.ok(AddressMapper.toOutput(address));
    }

    @RequireModeratorRole
    @GetMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<AddressOutputDTO>> onList(@Valid PageInputDTO dto) {
        Pageable pageable = PageValidator.getPageable(Address.class, dto);
        Page<Address> page = service.doPage(pageable);
        return ResponseEntity.ok(page.map(AddressMapper::toOutput));
    }

    @RequireUserRole
    @GetMapping(path = "/by-user", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AddressOutputDTO>> onListByUser(Authentication authentication,
            @PathVariable Integer userId) {
        List<Address> addressList = service.doListByUser(AuthFilter.getOnlineUser(authentication), userId);
        return ResponseEntity.ok(addressList.stream().map(AddressMapper::toOutput).toList());
    }
}