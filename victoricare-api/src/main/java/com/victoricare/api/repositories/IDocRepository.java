package com.victoricare.api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.victoricare.api.entities.Doc;

@Repository
public interface IDocRepository extends JpaRepository<Doc, Integer> {

    @Query("SELECT d from Doc d WHERE d.creationAuthor.id = ?1")
    List<Doc> findAllByUser(Integer userId);

}
