package com.victoricare.api.entities;

import java.util.Date;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Data
@NoArgsConstructor // JPA requires a no-arg constructor
@AllArgsConstructor // JPA requires an all-args constructor
@Builder
@Entity
public class Code {
    @Id
    @Column(name = "code_id", nullable = false, length = 10)
    private String id;

    @Column(name = "code_creation_date", nullable = false)
    private Date creationDate;
}
