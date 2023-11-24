package com.victoricare.api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.victoricare.api.entities.ChildCare;

@Repository
public interface IChildCareRepository extends JpaRepository<ChildCare, Long> {

    @Query("SELECT cc from ChildCare cc WHERE cc.creationFamilyId = ?1 AND cc.deletionDate = null")
    List<ChildCare> findAllByFamily(Integer id);

}
