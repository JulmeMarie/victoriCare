package com.victoricare.api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.victoricare.api.entities.Child;

@Repository
public interface IChildRepository extends JpaRepository<Child, Integer> {

    @Query("SELECT c from Child c WHERE c.family.id = ?1 AND c.deletionDate = null")
    List<Child> findAllByFamily(Integer id);

}
