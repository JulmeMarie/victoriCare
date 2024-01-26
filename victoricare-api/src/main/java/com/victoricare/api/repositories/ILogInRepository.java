package com.victoricare.api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.victoricare.api.entities.LogIn;

@Repository
public interface ILogInRepository extends JpaRepository<LogIn, Long> {

    List<LogIn> findByToken(String token);

    List<LogIn> findByPseudoAndToken(String pseudo, String token);

}
