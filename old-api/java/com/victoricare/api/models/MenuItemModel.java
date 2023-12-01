package com.victoricare.api.models;

import java.util.Date;

import com.victoricare.api.entities.MenuItem;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuItemModel {
private Integer id;

	private String href;

	private String text;

	private String type;

	private boolean active;

	private String group;

	private Integer order;

	private String target;

	private String htmlClass;

	private String icon;

	private Date createAt;

	private Date deleteAt;

	private Date updateAt;

	private Integer createBy;

	private Integer updateBy;

	private Integer deleteBy;

	public static MenuItemModel newInstance() {
		return new MenuItemModel();
	}


	public MenuItemModel init(MenuItem menuItem) {
		if(menuItem == null ) {
			return null;
		}
		this.id = menuItem.getIdMenuItem();
		this.type = menuItem.getTypeMenuItem();
		this.active = menuItem.isActiveMenuItem();
		this.text = menuItem.getTextMenuItem();
		this.href = menuItem.getHrefMenuItem();
		this.group = menuItem.getGroupMenuItem();
		this.order = menuItem.getOrderMenuItem();
		this.target = menuItem.getTargetMenuItem();
		this.htmlClass = menuItem.getHtmlClassMenuItem();
		this.icon = menuItem.getIconMenuItem();
		this.createAt = menuItem.getCreateAtMenuItem();
		this.deleteAt = menuItem.getDeleteAtMenuItem();
		this.updateAt = menuItem.getUpdateAtMenuItem();
		this.createBy = menuItem.getCreateByMenuItem() != null ? menuItem.getCreateByMenuItem().getIdUser() : null;
		this.deleteBy = menuItem.getDeleteByMenuItem() != null ? menuItem.getDeleteByMenuItem().getIdUser() : null;
		this.updateBy = menuItem.getUpdateByMenuItem() != null ? menuItem.getUpdateByMenuItem().getIdUser() : null;
		return this;
	}

}
