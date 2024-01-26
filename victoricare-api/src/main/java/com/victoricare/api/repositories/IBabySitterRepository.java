package com.victoricare.api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.victoricare.api.entities.BabySitter;

@Repository
public interface IBabySitterRepository extends JpaRepository<BabySitter, Long> {

    @Query("SELECT bs from BabySitter bs WHERE bs.family.id = ?1 AND bs.deletionDate = null")
    List<BabySitter> findAllByFamilyId(Integer familyId);

    @Query("SELECT bs from BabySitter bs WHERE bs.userId = ?1 AND bs.deletionDate = null")
    List<BabySitter> findByUserId(Integer userId);

}
