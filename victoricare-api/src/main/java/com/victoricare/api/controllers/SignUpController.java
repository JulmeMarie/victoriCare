package com.victoricare.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.victoricare.api.dtos.inputs.PageInputDTO;
import com.victoricare.api.dtos.inputs.SignUpInputDTO;
import com.victoricare.api.dtos.outputs.SignUpOutputDTO;
import com.victoricare.api.entities.SignUp;
import com.victoricare.api.mappers.SignUpMapper;
import com.victoricare.api.security.AuthFilter;
import com.victoricare.api.security.JWTToken;
import com.victoricare.api.services.ISignUpService;
import com.victoricare.api.validators.impl.PageValidator;
import com.victoricare.api.security.roles.RequireAdminRole;

import jakarta.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/signups")
public class SignUpController {

    @Autowired
    private ISignUpService signUpService;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JWTToken jwtToken;

    @PostMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE, consumes = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<SignUpOutputDTO> onSignUp(@Valid @RequestBody SignUpInputDTO dto) {
        jwtToken.init();
        SignUp signUp = signUpService.doCreate(jwtToken, encoder, dto);
        return ResponseEntity.ok(SignUpMapper.toOutput(signUp));
    }

    @PutMapping(path = "/confirm/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<SignUpOutputDTO> onValidate(@RequestBody Integer code, @PathVariable Integer id) {
        jwtToken.init();
        SignUp signUp = signUpService.doValidate(jwtToken, id, code);
        return ResponseEntity.ok(SignUpMapper.toOutput(signUp));
    }

    @PutMapping(path = "/cancel/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<String> onCancel(@PathVariable Integer id) {
        signUpService.doCancel(id);
        return ResponseEntity.ok().build();
    }

    @RequireAdminRole
    @DeleteMapping(path = "/{id}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public void onDelete(Authentication authentication, @PathVariable Integer id) {
        signUpService.doDelete(AuthFilter.getOnlineUser(authentication), id);
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SignUpOutputDTO> onGet(@PathVariable Integer id) {
        SignUpOutputDTO output = SignUpMapper.toOutput(signUpService.doGet(id));
        return ResponseEntity.ok(output);
    }

    @GetMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<SignUpOutputDTO>> onPage(@Valid PageInputDTO dto) {
        Pageable pageable = PageValidator.getPageable(SignUp.class, dto);
        Page<SignUp> page = signUpService.doPage(pageable);
        return ResponseEntity.ok(page.map(SignUpMapper::toOutput));
    }
}
