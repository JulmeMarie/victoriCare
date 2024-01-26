package com.victoricare.api.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.victoricare.api.entities.Task;

@Repository
public interface ITaskRepository extends JpaRepository<Task, Long> {

    @Query("SELECT t from Task t WHERE t.creationAuthorId = ?1 AND t.deletionDate = null")
    List<Task> findAllByUser(Integer userId);

}
