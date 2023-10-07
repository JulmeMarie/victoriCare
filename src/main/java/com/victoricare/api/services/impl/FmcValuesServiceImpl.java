package com.victoricare.api.services.impl;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.victoricare.api.dtos.FmcValuesDTO;
import com.victoricare.api.entities.FmcValues;
import com.victoricare.api.entities.User;
import com.victoricare.api.exceptions.ResourceAlreadyExistsException;
import com.victoricare.api.models.FmcValuesModel;
import com.victoricare.api.repositories.FmcValuesRepository;
import com.victoricare.api.services.IFmcValuesService;

@Service
public class FmcValuesServiceImpl implements IFmcValuesService{
	private static final Logger logger = LoggerFactory.getLogger(FmcValuesServiceImpl.class);

	@Autowired
	private FmcValuesRepository fmcValuesRepository;

	@Override
	public FmcValuesModel create(User author, FmcValuesDTO appRequest) {
		Date now = Date.from(Instant.now());
		this.fmcValuesRepository.findByTextFmcValues(appRequest.getText()).ifPresent((fmcValues)-> {
			logger.error("fmcValues already exists with key : {}", appRequest.getText());
			throw new ResourceAlreadyExistsException();
		});

		FmcValues fmcValues = new FmcValues();
		fmcValues.setCreateAtFmcValues(now);
		fmcValues.setCreateByFmcValues(author);
		fmcValues.setActiveFmcValues(appRequest.isActive());
		fmcValues.setKeyFmcValues(appRequest.getKey());
		fmcValues.setTypeFmcValues(appRequest.getType());
		fmcValues.setTextFmcValues(appRequest.getText());
		this.fmcValuesRepository.save(fmcValues);
		return FmcValuesModel.newInstance().init(fmcValues);
	}

	@Override
	public FmcValuesModel update(User author, FmcValuesDTO appRequest) {
		Date now = Date.from(Instant.now());

		FmcValues fmcValues = this.fmcValuesRepository
			.findById(appRequest.getId())
			.orElseThrow(
				()-> {
					logger.error("fmcValues already exists with id : {}", appRequest);
					throw new ResourceAlreadyExistsException();
				}
			);
		fmcValues.setUpdateAtFmcValues(now);
		fmcValues.setUpdateByFmcValues(author);
		fmcValues.setActiveFmcValues(appRequest.isActive());
		fmcValues.setKeyFmcValues(appRequest.getKey());
		fmcValues.setTypeFmcValues(appRequest.getType());
		fmcValues.setTextFmcValues(appRequest.getText());
		this.fmcValuesRepository.save(fmcValues);
		return FmcValuesModel.newInstance().init(fmcValues);
	}

	@Override
	public void delete(User author, Integer fmcValuesId) {
		FmcValues fmcValues = this.fmcValuesRepository.findById(fmcValuesId).orElseThrow(
			()-> {
				logger.error("fmcValues already exists with id : {}", fmcValuesId);
				throw new ResourceAlreadyExistsException();
			});
		fmcValues.setDeleteAtFmcValues(Date.from(Instant.now()));
		fmcValues.setActiveFmcValues(false);
		fmcValues.setDeleteByFmcValues(author);
		this.fmcValuesRepository.save(fmcValues);
	}

	@Override
	public FmcValuesModel unique(Integer fmcValuesId) {
		FmcValues fmcValues = this.fmcValuesRepository.findById(fmcValuesId).orElseThrow(
			()-> {
				logger.error("fmcValues already exists with id : {}", fmcValuesId);
				throw new ResourceAlreadyExistsException();
			});
		return FmcValuesModel.newInstance().init(fmcValues);
	}

	@Override
	public List<FmcValuesModel> list() {
		return this.fmcValuesRepository.findAll().stream().map(
				fmcValues ->  FmcValuesModel.newInstance().init(fmcValues)).collect(Collectors.toList());
	}
}
