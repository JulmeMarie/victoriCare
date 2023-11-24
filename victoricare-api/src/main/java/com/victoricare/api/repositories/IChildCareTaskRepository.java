package com.victoricare.api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.victoricare.api.entities.ChildCareTask;

@Repository
public interface IChildCareTaskRepository extends JpaRepository<ChildCareTask, Long> {

    @Query("SELECT cct from ChildCareTask cct WHERE cct.childCare.id = ?1 AND cct.deletionDate = null")
    List<ChildCareTask> findAllByChildCare(Long childCareId);

}
