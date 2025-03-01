package com.example.diplom.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

import static com.example.diplom.model.ProgressStatus.NOT_STARTED;

@Data
@Entity
@NoArgsConstructor
@Table(name="orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column( name="name", nullable=false)
    private String name;


    @Column( name="status",  nullable=false)
    @Enumerated(EnumType.STRING)
    private ProgressStatus progressStatus;


    @Column( name="created_at", nullable=false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    public Order(String name) {
        this.name = name;
        this.progressStatus=NOT_STARTED;
    }
}
