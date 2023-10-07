package com.victoricare.api.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.victoricare.api.entities.Contact;
import com.victoricare.api.entities.User;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Integer>{


	@Query("SELECT c from Contact c WHERE c.deleteAtContact is NULL ORDER BY c.idContact DESC")
	public List<Contact> findNonDeletedAll();

	@Modifying
	@Query("UPDATE Contact c SET c.deleteByContact = ?1 WHERE c.idContact = ?2")
	public void customDeleteById(User admin, Integer contactId);

	@Query("SELECT c from Contact c WHERE c.idContact = ?1 AND c.deleteAtContact is NULL")
	public Optional<Contact> findNonDeletedById(Integer contactId);

}
