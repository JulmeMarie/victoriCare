package com.victoricare.api.models;

import java.util.Date;

import com.victoricare.api.entities.FmcValues;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FmcValuesModel {

	@NotNull
	public Integer id;

	@Nullable
	public String text;

	@Nullable
	public String type;

	@NotNull
	public boolean active;

	@Nullable
	public String key;

	@Nullable
	public String url;

	@NotNull
	private Date createAt;

	@Nullable
	private Date deleteAt;

	@Nullable
	private Date updateAt;

	@NotNull
	private Integer createBy;

	@Nullable
	private Integer updateBy;

	@Nullable
	private Integer deleteBy;

	public static FmcValuesModel newInstance() {
		return new FmcValuesModel();
	}

	public FmcValuesModel init(FmcValues fmcValues) {
		if( fmcValues == null ) {
			return null;
		}
		this.id= fmcValues.getIdFmcValues();
		this.type = fmcValues.getTypeFmcValues();
		this.active = fmcValues.isActiveFmcValues();
		this.key = fmcValues.getKeyFmcValues();
		this.text = fmcValues.getTextFmcValues();
		this.createAt = fmcValues.getCreateAtFmcValues();
		this.deleteAt = fmcValues.getDeleteAtFmcValues();
		this.updateAt = fmcValues.getUpdateAtFmcValues();

		this.createBy = fmcValues.getCreateByFmcValues() != null ? fmcValues.getCreateByFmcValues().getIdUser() : null;
		this.deleteBy = fmcValues.getDeleteByFmcValues() != null ? fmcValues.getDeleteByFmcValues().getIdUser() : null;
		this.updateBy = fmcValues.getUpdateByFmcValues() != null ? fmcValues.getUpdateByFmcValues().getIdUser() : null;
		return this;
	}

}
