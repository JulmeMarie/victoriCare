package com.victoricare.api.services.impl;

import com.victoricare.api.repositories.IDeletionHistoryRepository;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.victoricare.api.services.IDeletionHistoryService;
import com.victoricare.api.entities.DeletionHistory;

@Service
public class DeletionHistoryService implements IDeletionHistoryService {

    @Autowired
    private IDeletionHistoryRepository repository;

    public <T> void doCreate(T t, Integer userId) {
        DeletionHistory deletion = new DeletionHistory();
        deletion.setCreationAuthorId(userId);
        deletion.setCreationDate(new Date());
        deletion.setLine(t.toString());
        deletion.setTable(t.getClass().getName());
        this.repository.save(deletion);
    }

}
