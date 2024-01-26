package com.victoricare.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.victoricare.api.entities.DeletionHistory;

@Repository
public interface IDeletionHistoryRepository extends JpaRepository<DeletionHistory, Long> {

}
