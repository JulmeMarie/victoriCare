package com.victoricare.api.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.victoricare.api.entities.Contact;

@Repository
public interface IContactRepository extends JpaRepository<Contact, Integer> {

    @Query("SELECT c from Contact c WHERE c.responseAuthor.id = ?1")
    List<Contact> findAllByUser(Integer userId);

}
