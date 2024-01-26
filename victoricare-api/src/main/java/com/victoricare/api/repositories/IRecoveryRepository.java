package com.victoricare.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.victoricare.api.entities.Recovery;

@Repository
public interface IRecoveryRepository extends JpaRepository<Recovery, Integer> {

    @Query("SELECT r FROM Recovery r WHERE r.email = :email AND r.deletionDate IS NULL  ORDER BY r.codeDate DESC LIMIT 1")
    Recovery findByEmail(String email);

}
