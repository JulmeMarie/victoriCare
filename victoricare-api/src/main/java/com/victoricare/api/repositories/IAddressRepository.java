package com.victoricare.api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.victoricare.api.entities.Address;

@Repository
public interface IAddressRepository extends JpaRepository<Address, Integer> {

    @Query("SELECT a from Address a WHERE a.creationAuthorId = ?1 AND a.deletionDate IS NOT NULL")
    List<Address> findAllByUserId(Integer userId);

}
