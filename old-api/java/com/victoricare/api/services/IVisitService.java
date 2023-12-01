package com.victoricare.api.services;

import java.util.List;

import com.victoricare.api.entities.User;
import com.victoricare.api.enums.EPeriod;
import com.victoricare.api.models.VisitModel;

import jakarta.servlet.http.HttpServletRequest;

public interface IVisitService {

	public VisitModel create(HttpServletRequest request, User author, String type);

	public VisitModel unique(Integer visitId);

	public List<VisitModel> list(EPeriod period);
}
