package com.victoricare.api.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.victoricare.api.entities.Connection;
import com.victoricare.api.entities.User;

@Transactional
@Repository
public interface ConnectionRepository extends JpaRepository<Connection, Integer>{


	@Query("SELECT c from Connection c WHERE c.deleteAtConnection is NULL")
	public List<Connection> findNonDeletedAll();

	@Query("SELECT c from Connection c WHERE (c.loginConnection = ?1 OR c.loginConnection = ?2) AND c.ipConnection = ?3 AND c.browserConnection = ?4 AND c.deleteAtConnection is NULL")
	public Optional<List<Connection>> findByIdentifiers(String username, String email, String ip, String browser);

	@Query("SELECT c from Connection c WHERE c.tokenConnection = ?1")
	public List<Connection> findByTokenConnection(String token);

	@Modifying
	@Query("UPDATE Connection c SET deleteAtConnection = NOW(), deleteByConnection = ?4 WHERE c.loginConnection = ?1 AND c.ipConnection = ?2 AND c.browserConnection = ?3 AND c.deleteAtConnection is NULL")
	public void endAllConnections(String login, String userIp, String userBrowser, User author);

	@Modifying
    @Query("UPDATE Connection c SET deleteAtConnection = NOW(), deleteByConnection = ?2 WHERE c.createAtConnection = ?1 AND c.deleteAtConnection is NULL")
	public void endAllConnections(User user, User author);
}
