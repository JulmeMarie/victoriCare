package com.victoricare.api.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Content {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idContent;

	@Column(name = "type_content", nullable = false)
	private String typeContent;

	@Column(name = "title_content", nullable = true)
	private String titleContent;

	@Column(name = "create_at_content", nullable = true)
	private Date createAtContent;

	@Column(name = "delete_at_content", nullable = true)
	private Date deleteAtContent;

	@Column(name = "update_at_content", nullable = true)
	private Date updateAtContent;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "create_by_content", nullable = true)
	private User createByContent;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "update_by_content", nullable = true)
	private User updateByContent;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "delete_by_content", nullable = true)
	private User deleteByContent;

	@OneToMany(fetch = FetchType.EAGER, mappedBy="contentSection", cascade = CascadeType.ALL)
	private List<Section>sections = new ArrayList<>();
}
