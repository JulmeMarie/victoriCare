package com.victoricare.api.models;
import java.util.Date;
import com.victoricare.api.entities.CarouselItem;
import com.victoricare.api.services.IFileService;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarouselItemModel {
	@Nullable
	private Integer id;

	private Short order;

	private String urlFile;

	private String title;

	private String description;

	private Date createAt;

	private Date deleteAt;

	private Date updateAt;

	private Integer createBy;

	private Integer updateBy;

	private Integer deleteBy;

	public static CarouselItemModel newInstance() {
		return new CarouselItemModel();
	}

	public CarouselItemModel init(IFileService fileService, CarouselItem carouselItem) {
		if(carouselItem == null) return null;
		this.id = carouselItem.getIdCarouselItem();
        this.order = carouselItem.getOrderCarouselItem();
        this.title = carouselItem.getTitleCarouselItem();
        this.description = carouselItem.getDescriptionCarouselItem();
        this.createAt = carouselItem.getCreateAtCarouselItem();
        this.updateAt = carouselItem.getUpdateAtCarouselItem();
        this.deleteAt = carouselItem.getDeleteAtCarouselItem();
        this.createBy = carouselItem.getCreateByCarouselItem() != null ? carouselItem.getCreateByCarouselItem().getIdUser() : null;
		this.deleteBy = carouselItem.getDeleteByCarouselItem() != null ? carouselItem.getDeleteByCarouselItem().getIdUser() : null;
		this.updateBy = carouselItem.getUpdateByCarouselItem() != null ? carouselItem.getUpdateByCarouselItem().getIdUser() : null;
		this.urlFile = carouselItem.getPhotoCarouselItem() != null ? fileService.getFileUrl(carouselItem.getPhotoCarouselItem()) : null;
        return this;
	}
}
