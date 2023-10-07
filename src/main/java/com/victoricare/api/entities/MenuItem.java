package com.victoricare.api.entities;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class MenuItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idMenuItem;

	@Column(name = "href_menu_item", nullable = true)
	private String hrefMenuItem;

	@Column(name = "text_menu_item", nullable = true)
	private String textMenuItem;

	@Column(name = "type_menu_item", nullable = true)
	private String typeMenuItem;

	@Column(name = "active_menu_item", nullable = false)
	private boolean activeMenuItem;

	@Column(name = "group_menu_item", nullable = true)
	private String groupMenuItem;


	@Column(name = "order_menu_item", nullable = true)
	private Integer orderMenuItem;

	@Column(name = "target_menu_item", nullable = true)
	private String targetMenuItem;

	@Column(name = "html_class_menu_item", nullable = true)
	private String htmlClassMenuItem;

	@Column(name = "icon_menu_item", nullable = true)
	private String iconMenuItem;

	@Column(name = "create_at_menuItem", nullable = true)
	private Date createAtMenuItem;

	@Column(name = "delete_at_menuItem", nullable = true)
	private Date deleteAtMenuItem;

	@Column(name = "update_at_menuItem", nullable = true)
	private Date updateAtMenuItem;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "create_by_menuItem", nullable = true)
	private User createByMenuItem;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "update_by_menuItem", nullable = true)
	private User updateByMenuItem;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "delete_by_menuItem", nullable = true)
	private User deleteByMenuItem;
}
