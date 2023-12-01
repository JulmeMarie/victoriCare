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
public class FmcValues {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idFmcValues;

	@Column(name = "key_fmc_values", nullable = false)
	private String keyFmcValues;

	@Column(name = "text_fmc_values", nullable = false)
	private String textFmcValues;

	@Column(name = "type_fmc_values", nullable = false)
	private String typeFmcValues;

	@Column(name = "active_fmc_values", nullable = false)
	private boolean activeFmcValues;

	@Column(name = "create_at_fmc_values", nullable = true)
	private Date createAtFmcValues;

	@Column(name = "delete_at_fmc_values", nullable = true)
	private Date deleteAtFmcValues;

	@Column(name = "update_at_fmc_values", nullable = true)
	private Date updateAtFmcValues;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "create_by_fmc_values", nullable = true)
	private User createByFmcValues;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "update_by_fmc_values", nullable = true)
	private User updateByFmcValues;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "delete_by_fmc_values", nullable = true)
	private User deleteByFmcValues;
}
