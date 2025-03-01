package com.example.diplom.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;

import static com.example.diplom.model.ProgressStatus.NOT_STARTED;

@Data
@Entity
@NoArgsConstructor
@Table(name="pizzas")
public class Pizza {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    @Column( name="name", nullable=false)
    private String name;


    @Column( name="status",  nullable=false)
    @Enumerated(EnumType.STRING)
    private ProgressStatus progressStatus;



    @JoinColumn (name="order_id", nullable=true)
    @ManyToOne
//    @JoinColumn(name="orderId")
    private Order order;

    public Pizza(String name) {
        this.name = name;
        this.progressStatus=NOT_STARTED;

    }
}
