package com.victoricare.api.services.impl;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.victoricare.api.dtos.CarouselItemDTO;
import com.victoricare.api.entities.CarouselItem;
import com.victoricare.api.entities.User;
import com.victoricare.api.enums.EFolder;
import com.victoricare.api.exceptions.ResourceNotCreatedException;
import com.victoricare.api.exceptions.ResourceNotFoundException;
import com.victoricare.api.models.CarouselItemModel;
import com.victoricare.api.repositories.CarouselItemRepository;
import com.victoricare.api.security.jwt.JWTUtils;
import com.victoricare.api.services.ICarouselItemService;
import com.victoricare.api.services.IFileService;
@Service
public class CarouselItemServiceImpl implements ICarouselItemService{

	private static final Logger logger = LoggerFactory.getLogger(CarouselItemServiceImpl.class);

	@Autowired
	private CarouselItemRepository carouselItemRepository;
	
	@Autowired
	private JWTUtils jwtUtils;
	
	@Autowired
	private IFileService fileService;
	

	@Override
	public CarouselItemModel create(User author, CarouselItemDTO request) {
		if(request.getMFile()== null) {
			logger.error("carousel must have an image : {}");
			throw new ResourceNotCreatedException();
		}

		CarouselItem carouselItem = new CarouselItem();
		carouselItem.setCreateAtCarouselItem(Date.from(Instant.now()));
		carouselItem.setCreateByCarouselItem(author);
		carouselItem.setTitleCarouselItem(request.getTitle());
		carouselItem.setDescriptionCarouselItem(request.getDescription());
		carouselItem.setOrderCarouselItem(request.getOrder());
		if(!request.getMFile().isEmpty()) {
			this.fileService.setS3BucketName(this.jwtUtils.getS3BucketName());
			String filename = fileService.save(EFolder.CAROUSEL,request.getMFile());
			carouselItem.setPhotoCarouselItem(filename);
		}
		
		this.carouselItemRepository.save(carouselItem);
		fileService.setS3BucketUrl(this.jwtUtils.getS3BucketUrl());
		return CarouselItemModel.newInstance().init(fileService, carouselItem);
	}

	@Override
	public CarouselItemModel update(User author, CarouselItemDTO request) {

		Integer itemId = request.getId();
		CarouselItem carouselItem = this.carouselItemRepository.findNonDeletedById(itemId).orElseThrow(() -> {
			logger.error("carousel item not found for id : {}", itemId);
			throw new ResourceNotFoundException();
		});

		carouselItem.setUpdateAtCarouselItem(Date.from(Instant.now()));
		carouselItem.setUpdateByCarouselItem(author);
		carouselItem.setTitleCarouselItem(request.getTitle());
		carouselItem.setDescriptionCarouselItem(request.getDescription());
		carouselItem.setOrderCarouselItem(request.getOrder());

		if(!request.getMFile().isEmpty()) {
			this.fileService.setS3BucketName(this.jwtUtils.getS3BucketName());
			fileService.delete(carouselItem.getPhotoCarouselItem());
			String filename = fileService.save(EFolder.CAROUSEL,request.getMFile());
			carouselItem.setPhotoCarouselItem(filename);
		}
		this.carouselItemRepository.save(carouselItem);
		fileService.setS3BucketUrl(this.jwtUtils.getS3BucketUrl());
		return CarouselItemModel.newInstance().init(fileService, carouselItem);
	}

	@Override
	public void delete(User author, Integer itemId) {

		CarouselItem item =  this.carouselItemRepository.findNonDeletedById(itemId).orElseThrow(()-> new ResourceNotFoundException());
		item.setDeleteAtCarouselItem(Date.from(Instant.now()));
		item.setDeleteByCarouselItem(author);
		this.carouselItemRepository.save(item);
	}

	@Override
	public CarouselItemModel unique(Integer itemId) {
		fileService.setS3BucketUrl(this.jwtUtils.getS3BucketUrl());
		CarouselItem carouselItem = this.carouselItemRepository.findNonDeletedById(itemId).orElseThrow(() -> {
			logger.error("carousel item not found for id : {}", itemId);
			throw new ResourceNotFoundException();
		});
		return CarouselItemModel.newInstance().init(fileService, carouselItem);
	}

	@Override
	public List<CarouselItemModel> list() {
		fileService.setS3BucketUrl(this.jwtUtils.getS3BucketUrl());
		List<CarouselItem> carouselItemList = this.carouselItemRepository.findNonDeletedAll();
		return carouselItemList
			.stream()
			.map(carouselItem -> CarouselItemModel.newInstance().init(fileService, carouselItem))
			.collect(Collectors.toList());
	}
}
