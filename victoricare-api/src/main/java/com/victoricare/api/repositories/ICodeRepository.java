package com.victoricare.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.victoricare.api.entities.Code;

public interface ICodeRepository extends JpaRepository<Code, String> {

    @Query("SELECT EXISTS(SELECT 1 FROM Code c WHERE c.id = ?1) AS value_exists")
    boolean existsByCode(String code);

}
