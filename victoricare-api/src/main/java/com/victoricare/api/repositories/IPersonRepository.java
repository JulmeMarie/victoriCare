package com.victoricare.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.victoricare.api.entities.Person;

@Repository
public interface IPersonRepository extends JpaRepository<Person, Integer> {

    @Query("SELECT EXISTS(SELECT 1 FROM Person p WHERE p.uniqueIdentifier = ?1) AS value_exists")
    boolean existsByCode(String code);

}
