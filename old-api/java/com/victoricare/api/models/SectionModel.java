package com.victoricare.api.models;
import java.util.Date;
import com.victoricare.api.entities.Section;
import com.victoricare.api.services.IFileService;
import com.victoricare.api.services.impl.FileServiceImpl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SectionModel {

	private Integer id;

	private String title;

	private String text;

    private String leftUrlFile;

	private String rightUrlFile;

	private Integer order;

	private String htmlClass;

	private Date createAt;

	private Date updateAt;

	private Date deleteAt;

	private Integer createBy;

	private Integer updateBy;

	private Integer deleteBy;

	public static SectionModel newInstance() {
		return new SectionModel();
	}

	public SectionModel init(IFileService fileService, Section section) {
		if(section == null ) return null;
		this.id = section.getIdSection();
		this.title = section.getTitleSection();
		this.text = section.getTextSection();
		this.htmlClass = section.getHtmlClassSection();
		this.order = section.getOrderSection();
		this.createAt = section.getCreateAtSection();
		this.deleteAt = section.getDeleteAtSection();
		this.updateAt = section.getUpdateAtSection();
		this.createBy = section.getCreateBySection() != null ? section.getCreateBySection().getIdUser() : null;
		this.deleteBy = section.getDeleteBySection() != null ? section.getDeleteBySection().getIdUser() : null;
		this.updateBy = section.getUpdateBySection() != null ? section.getUpdateBySection().getIdUser() : null;

		this.leftUrlFile = section.getLeftImageSection() != null ? fileService.getFileUrl(section.getLeftImageSection()) : null;
		this.rightUrlFile = section.getRightImageSection() != null ? fileService.getFileUrl(section.getRightImageSection()) : null;
		return this;
	}
}
