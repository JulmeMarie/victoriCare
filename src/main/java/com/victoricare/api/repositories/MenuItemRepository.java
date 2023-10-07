package com.victoricare.api.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.victoricare.api.entities.MenuItem;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Integer>{

	public Optional<MenuItem> findByHrefMenuItem(String key);

	@Query("SELECT l from MenuItem l WHERE l.deleteAtMenuItem is NULL order by l.orderMenuItem")
	public List<MenuItem> findNonDeletedAll();

}
