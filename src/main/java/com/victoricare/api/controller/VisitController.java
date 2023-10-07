package com.victoricare.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.victoricare.api.enums.EPeriod;
import com.victoricare.api.models.VisitModel;
import com.victoricare.api.security.jwt.AuthTokenFilter;
import com.victoricare.api.services.IVisitService;

import jakarta.servlet.http.HttpServletRequest;

@CrossOrigin(origins={"*"}, maxAge=3600L)
@Validated
@RestController
public class VisitController
{
  @Autowired
  public IVisitService visitService;

  @PutMapping(path={"/api/right-anm/visits"}, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<VisitModel> create(HttpServletRequest request, Authentication authentication, @RequestBody String type)
  {
    return ResponseEntity.ok(visitService.create(request, AuthTokenFilter.getOnlineUser(authentication), type));
  }

  @GetMapping(path="/api/right-anm/visits/{period}",  produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<VisitModel>> list(@PathVariable String period)
  {
    return ResponseEntity.ok(visitService.list(EPeriod.get(period)));
  }
}