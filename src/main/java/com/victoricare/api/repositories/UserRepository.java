package com.victoricare.api.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.victoricare.api.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

	@Query("SELECT c from User c WHERE usernameUser = ?1 AND c.deleteAtUser is NULL AND c.accountValidateAtUser is NOT NULL")
	public Optional<User> findNotDeletedByIdentifier(String username);

	@Query("SELECT c from User c WHERE (c.usernameUser = ?1 OR c.emailUser = ?1) AND c.deleteAtUser is NULL")
	public Optional<User> findByUsernameUser(String username);

	public Optional<User> findByEmailUser(String email);

	@Override
	public User save(User user);

	@Query("SELECT c from User c WHERE roleUser in(?1) AND c.deleteAtUser is NULL AND c.accountValidateAtUser is NOT NULL")
	public Optional<List<User>> findAllUsers(List<String> roles);

	@Query("SELECT c from User c WHERE isContactAddresseeUser = true AND c.deleteAtUser is NULL AND c.accountValidateAtUser is NOT NULL")
	public Optional<List<User>> findAllContactAddressees();
}
