package com.victoricare.api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.victoricare.api.entities.SignUp;

@Repository
public interface ISignUpRepository extends JpaRepository<SignUp, Integer> {

    @Query("SELECT s from SignUp s WHERE s.email = ?1 OR s.pseudo = ?2")
    List<SignUp> findByEmailOrPseudo(String email, String pseudo);

}
