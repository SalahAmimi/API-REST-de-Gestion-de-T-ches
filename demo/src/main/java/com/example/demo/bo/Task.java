package com.example.demo.bo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task {
    @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 50,nullable = false)
    private String titre;

    @Column(length = 255)
    private String description;

    //@Temporal(TemporalType.DATE)
    private LocalDateTime dateCreation;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Estatut statut;

}
