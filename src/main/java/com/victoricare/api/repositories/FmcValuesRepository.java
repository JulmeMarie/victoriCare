package com.victoricare.api.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.victoricare.api.entities.FmcValues;

@Repository
public interface FmcValuesRepository extends JpaRepository<FmcValues, Integer>{


	@Query("SELECT c from FmcValues c WHERE c.deleteAtFmcValues is NULL")
	public List<FmcValues> findNonDeletedAll();

	public Optional<FmcValues> findByTextFmcValues(String text);
}
