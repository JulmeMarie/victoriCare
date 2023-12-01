package com.victoricare.api.services.impl;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.victoricare.api.entities.User;
import com.victoricare.api.entities.Visit;
import com.victoricare.api.enums.EPeriod;
import com.victoricare.api.exceptions.ResourceNotFoundException;
import com.victoricare.api.models.VisitModel;
import com.victoricare.api.repositories.VisitRepository;
import com.victoricare.api.security.jwt.JWTUtils;
import com.victoricare.api.services.IVisitService;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class VisitServiceImpl implements IVisitService{
	private static final Logger logger = LoggerFactory.getLogger(VisitServiceImpl.class);

	@Autowired
	private VisitRepository visitRepository;

	@Autowired
	private JWTUtils jwtUtils;

	@Override
	public VisitModel create(HttpServletRequest request, User author, String type) {
		Date now = Date.from(Instant.now());
		Visit visit = new Visit();
		visit.setCreateAtVisit(now);
		visit.setContentTypeVisit(type);
		visit.setIpVisit(this.jwtUtils.getIp(request));
		visit.setNavVisit(this.jwtUtils.getBrowser(request));

		if(author != null) {
		  visit.setUsernameVisit(author.getUsernameUser());
		}
		this.visitRepository.save(visit);
		return VisitModel.newInstance().init(visit);
	}


	@Override
	public VisitModel unique(Integer visitId) {
		Visit visit = this.visitRepository.findById(visitId).orElseThrow(
			()-> {
				logger.error("visit does not exist with id : {}", visitId);
				throw new ResourceNotFoundException();
			});
		return VisitModel.newInstance().init(visit);
	}

	@Override
	public List<VisitModel> list(EPeriod period) {
        if(period == EPeriod.UNKNOWN) {
			throw new ResourceNotFoundException();
		}
		Optional<List<Visit>> visitList = Optional.empty();
		if(period == EPeriod.TODAY) {
 			visitList = this.visitRepository.findAllToday();
		}
		else if(period == EPeriod.WEEK) {
 			visitList = this.visitRepository.findAllWeek();
		}
		else if(period == EPeriod.MONTH) {
 			visitList = this.visitRepository.findAllMonth();
		}
		else if(period == EPeriod.YEAR) {
 			visitList = this.visitRepository.findAllYear();
		}
		
		if(visitList.isEmpty()) return null;
		return visitList.get().stream().map(
				visit ->  VisitModel.newInstance().init(visit)).collect(Collectors.toList());
	}
}
