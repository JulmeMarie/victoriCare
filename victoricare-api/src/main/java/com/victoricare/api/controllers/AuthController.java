package com.victoricare.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.victoricare.api.dtos.inputs.LogInInputDTO;
import com.victoricare.api.dtos.inputs.PageInputDTO;
import com.victoricare.api.dtos.outputs.LogInOutputDTO;
import com.victoricare.api.entities.LogIn;
import com.victoricare.api.mappers.LogInMapper;
import com.victoricare.api.security.AuthFilter;
import com.victoricare.api.security.JWTToken;
import com.victoricare.api.security.roles.RequireAdminRole;
import com.victoricare.api.security.roles.RequireAnonymousRole;
import com.victoricare.api.security.roles.RequireModeratorRole;
import com.victoricare.api.security.roles.RequireSuperAdminRole;
import com.victoricare.api.security.roles.RequireUserRole;
import com.victoricare.api.services.ILogInService;
import com.victoricare.api.validators.impl.PageValidator;

import jakarta.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auths")
public class AuthController {

    @Autowired
    private ILogInService service;

    @Autowired
    private JWTToken jwtToken;

    @RequireAnonymousRole
    @PostMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE, consumes = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<LogInOutputDTO> onLogIn(AuthenticationManager authenticationManager, JWTToken jwtToken,
            @Valid @RequestBody LogInInputDTO dto) {
        jwtToken.init();
        LogIn login = service.doCreate(authenticationManager, jwtToken, dto);
        return ResponseEntity.ok(LogInMapper.toOutput(login));
    }

    @RequireUserRole
    @GetMapping(path = "/logout")
    public void onLogOut(Authentication authentication, @PathVariable Long id) {
        service.doLogOut(AuthFilter.getOnlineUser(authentication), id);
    }

    @GetMapping(path = "/token")
    public ResponseEntity<String> onGetToken(Authentication authentication) {
        jwtToken.init();
        jwtToken.checkApiKey();
        return ResponseEntity.ok(service.doCreateToken(jwtToken));
    }

    @GetMapping(path = "/refresh-token")
    public ResponseEntity<String> onRefreshToken(Authentication authentication) {
        jwtToken.init();
        jwtToken.checkApiKey();
        return ResponseEntity.ok(service.doRefreshToken(AuthFilter.getOnlineUser(authentication), jwtToken));
    }

    @RequireSuperAdminRole
    @DeleteMapping(path = "/{id}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public void onDelete(Authentication authentication,
            @PathVariable Long id) {
        service.doDelete(AuthFilter.getOnlineUser(authentication), id);
    }

    @RequireAdminRole
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LogInOutputDTO> onGet(@PathVariable Long id) {
        LogIn login = service.doGet(id);
        return ResponseEntity.ok(LogInMapper.toOutput(login));
    }

    @RequireModeratorRole
    @GetMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<LogInOutputDTO>> onList(@Valid PageInputDTO dto) {
        Pageable pageable = PageValidator.getPageable(LogIn.class, dto);
        Page<LogIn> loginList = service.doPage(pageable);
        return ResponseEntity.ok(loginList.map(LogInMapper::toOutput));
    }
}
