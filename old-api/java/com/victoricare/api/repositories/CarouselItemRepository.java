package com.victoricare.api.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.victoricare.api.entities.CarouselItem;

@Repository
public interface CarouselItemRepository extends JpaRepository<CarouselItem, Integer>{


	@Query("SELECT c from CarouselItem c WHERE c.deleteAtCarouselItem is NULL")
	public List<CarouselItem> findNonDeletedAll();

	@Query("SELECT c from CarouselItem c WHERE c.idCarouselItem = ?1 AND c.deleteAtCarouselItem is NULL")
	public Optional<CarouselItem> findNonDeletedById(Integer itemId);

}
