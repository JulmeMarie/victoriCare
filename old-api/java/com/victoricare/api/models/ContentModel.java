package com.victoricare.api.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.victoricare.api.entities.Content;
import com.victoricare.api.services.IFileService;
import com.victoricare.api.services.impl.FileServiceImpl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContentModel {

	private Integer id;

	private String type;

	private String title;

	private Date createAt;

	private Date deleteAt;

	private Date updateAt;

	private Integer createBy;

	private Integer updateBy;

	private Integer deleteBy;

	private List<SectionModel>sections = new ArrayList<>();

	public static ContentModel newInstance() {
		return new ContentModel();
	}

	public ContentModel init(IFileService fileService, Content content) {
		if(content == null) return null;
		this.id = content.getIdContent();
		this.type = content.getTypeContent();
		this.title = content.getTitleContent();
		this.createAt = content.getCreateAtContent();
		this.deleteAt = content.getDeleteAtContent();
		this.updateAt = content.getUpdateAtContent();
		this.createBy = content.getCreateByContent() != null ? content.getCreateByContent().getIdUser() : null;
		this.deleteBy = content.getDeleteByContent() != null ? content.getDeleteByContent().getIdUser() : null;
		this.updateBy = content.getUpdateByContent() != null ? content.getUpdateByContent().getIdUser() : null;

		if(content.getSections() != null && content.getSections().size() > 0) {
			content.getSections().forEach(section->{
				this.sections.add(SectionModel.newInstance().init(fileService, section));
			});
		}
		return this;
	}
}
