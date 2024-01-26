package com.victoricare.api.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.victoricare.api.entities.Section;

@Repository
public interface ISectionRepository extends JpaRepository<Section, Integer> {

    @Query("SELECT s from Section s WHERE s.doc.id = ?1 AND s.deletionDate = null")
    List<Section> findAllByDoc(Integer docId);

}
