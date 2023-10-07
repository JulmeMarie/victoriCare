package com.victoricare.api.services;

import java.util.List;

import com.victoricare.api.dtos.FmcValuesDTO;
import com.victoricare.api.entities.User;
import com.victoricare.api.models.FmcValuesModel;

public interface IFmcValuesService {

	public FmcValuesModel create(User author, FmcValuesDTO coordinateReq);

	public FmcValuesModel update(User author, FmcValuesDTO coordinateReq);

	public void delete(User author, Integer coordinateId);

	public FmcValuesModel unique(Integer coordinateId);

	public List<FmcValuesModel> list();
}
