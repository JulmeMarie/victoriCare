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
public class CarouselItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idCarouselItem;

	@Column(name = "order_carousel_Item", nullable = false)
	private Short orderCarouselItem;

	@Column(name = "photo_carousel_Item", nullable = false)
	private String photoCarouselItem;

	@Column(name = "title_carousel_Item", nullable = true)
	private String titleCarouselItem;

	@Column(name = "description_carousel_Item", nullable = true)
	private String descriptionCarouselItem;

	@Column(name = "create_at_carousel_item", nullable = true)
	private Date createAtCarouselItem;

	@Column(name = "delete_at_carousel_item", nullable = true)
	private Date deleteAtCarouselItem;

	@Column(name = "update_at_carouselItem", nullable = true)
	private Date updateAtCarouselItem;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "create_by_carousel_item", nullable = true)
	private User createByCarouselItem;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "update_by_carousel_item", nullable = true)
	private User updateByCarouselItem;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "delete_by_carousel_item", nullable = true)
	private User deleteByCarouselItem;

}
