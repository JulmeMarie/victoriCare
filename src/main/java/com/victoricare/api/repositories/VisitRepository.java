package com.victoricare.api.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.victoricare.api.entities.Visit;

@Repository
public interface VisitRepository extends JpaRepository<Visit, Integer>{

    //SELECT * from `fmc_db`.`visit` WHERE CAST(`create_at_visit` as date) = CAST(NOW() as date);

    @Query("SELECT v from Visit v WHERE CAST(v.createAtVisit as date) = CAST(NOW() as date)")
	public Optional<List<Visit>> findAllToday();

    @Query("SELECT v from Visit v WHERE YEAR(v.createAtVisit) = YEAR(NOW()) AND WEEK(v.createAtVisit) = WEEK(NOW())")
	public Optional<List<Visit>> findAllWeek();

    @Query("SELECT v from Visit v WHERE YEAR(v.createAtVisit) = YEAR(NOW()) AND MONTH(v.createAtVisit) = MONTH(NOW())")
	public Optional<List<Visit>> findAllMonth();

    @Query("SELECT v from Visit v WHERE YEAR(v.createAtVisit) = YEAR(NOW())")
	public Optional<List<Visit>> findAllYear();
}
