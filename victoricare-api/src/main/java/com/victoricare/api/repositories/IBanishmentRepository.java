package com.victoricare.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.victoricare.api.entities.Banishment;

@Repository
public interface IBanishmentRepository extends JpaRepository<Banishment, Integer> {

}
