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
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class Section {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idSection;

	@Column(name = "title_section", nullable = true)
	private String titleSection;

	@Column(name = "text_section", nullable = true)
	private String textSection;

	@Column(name = "left_image_section", nullable = true)
	private String leftImageSection;

	@Column(name = "right_image_section", nullable = true)
	private String rightImageSection;

	@Column(name = "order_section", nullable = false)
	private Integer orderSection;

	@Column(name = "html_class_section", nullable = true)
	private String htmlClassSection;

	@Column(name = "create_at_section", nullable = true)
	private Date createAtSection;

	@Column(name = "update_at_section", nullable = true)
	private Date updateAtSection;

	@Column(name = "delete_at_section", nullable = true)
	private Date deleteAtSection;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "create_by_section", nullable = true)
	private User createBySection;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "update_by_section", nullable = true)
	private User updateBySection;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "delete_by_section", nullable = true)
	private User deleteBySection;

	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "content_section", nullable = true)
	private Content contentSection;

}
