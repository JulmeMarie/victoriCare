package com.victoricare.api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.victoricare.api.entities.Media;

@Repository
public interface IMediaRepository extends JpaRepository<Media, Integer> {

    @Query("SELECT m from Media m WHERE m.section.id = ?1 AND m.deletionDate = null")
    List<Media> findAllBySection(Integer sectionId);

    @Query("SELECT m from Media m WHERE m.creationAuthorId = ?1 AND m.deletionDate IS NOT NULL")
    List<Media> findAllByUserId(Integer userId);

}
