package com.victoricare.api.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.victoricare.api.entities.User;

@Repository
public interface IUserRepository extends JpaRepository<User, Integer> {

    public Optional<User> findByPseudo(String pseudo);

    @Query("SELECT s from User s WHERE s.email = ?1 OR s.pseudo = ?2")
    List<User> findByEmailOrPseudo(String email, String pseudo);

    public Optional<User> findByEmail(String email);

    @Query("SELECT s from User s WHERE s.roles LIKE '%ADMINISTRATOR%'")
    public List<User> findAllAdmins();

}
