package org.example.backendenicargy.Models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.internal.IgnoreForbiddenApisErrors;


@Getter

@Setter

@NoArgsConstructor

@AllArgsConstructor

@ToString


@Entity
public class Reclamation {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titre;
    private String description;
    private String local;
    private String salle;

    @Column(columnDefinition = "VARCHAR(255) DEFAULT 'En_Attente'",insertable = false)
    private String status;


    @CreationTimestamp
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP",updatable = false , insertable = false)
    private String date;

    @ManyToOne
    private User user;


    private String photourl;
}