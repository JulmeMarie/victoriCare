package com.victoricare.api.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.victoricare.api.entities.Content;

@Repository
public interface ContentRepository extends JpaRepository<Content, Integer>{


	@Query("SELECT c from Content c WHERE c.deleteAtContent is NULL")
	public List<Content> findNonDeletedAll();

	@Query("SELECT c from Content c WHERE c.idContent = ?1 AND c.deleteAtContent is NULL")
	public Optional<Content> findNonDeletedById(Integer id);

	@Query("SELECT c from Content c WHERE c.typeContent in(?1) AND c.deleteAtContent is NULL")
	public List<Content> findNonDeletedAll(List<String> types);

}
