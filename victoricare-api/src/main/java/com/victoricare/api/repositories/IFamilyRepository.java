package com.victoricare.api.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.victoricare.api.entities.Family;

@Repository
public interface IFamilyRepository extends JpaRepository<Family, Integer> {

    @Query("SELECT f from Family f WHERE (f.creationAuthorId = ?1 OR f.parentOne.id = ?1 OR f.parentTwo.id = ?1) AND f.deletionDate = null")
    List<Family> findAllByParent(Integer parentId);

}
