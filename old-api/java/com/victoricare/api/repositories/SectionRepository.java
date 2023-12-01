package com.victoricare.api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.victoricare.api.entities.Section;

@Repository
public interface SectionRepository extends JpaRepository<Section, Integer>{


	@Query("SELECT c from Section c WHERE c.deleteAtSection is NULL")
	public List<Section> findNonDeletedAll();

}
