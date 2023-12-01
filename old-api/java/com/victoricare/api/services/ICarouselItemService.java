package com.victoricare.api.services;
import java.util.List;
import com.victoricare.api.dtos.CarouselItemDTO;
import com.victoricare.api.entities.User;
import com.victoricare.api.models.CarouselItemModel;

public interface ICarouselItemService {

	public CarouselItemModel create(User author, CarouselItemDTO request) ;

	public CarouselItemModel update(User author, CarouselItemDTO request);

	public void delete(User author, Integer itemId) ;

	public CarouselItemModel unique(Integer carouselItemId);

	public List<CarouselItemModel> list();
}
