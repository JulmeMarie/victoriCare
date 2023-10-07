package com.victoricare.api.models;

import java.util.Date;

import com.victoricare.api.entities.Visit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VisitModel {

	private Long id;

	private String ip;

	private String nav;

	private String username;

	private String contentType;

	private Date createAt;

	public static VisitModel newInstance() {
		return new VisitModel();
	}

	public VisitModel init(Visit visit) {
		if(visit == null) {
			return null;
		}
		this.id = visit.getIdVisit();
		this.username = visit.getUsernameVisit();
		this.ip = visit.getIpVisit();
		this.nav = visit.getNavVisit();
		this.contentType = visit.getContentTypeVisit();
		this.createAt = visit.getCreateAtVisit();
		return this;
	}
}
