package org.example.backendenicargy;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;


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
    private String status;

    @CreationTimestamp
    @Column(updatable = false)
    private String date;

    private Long userid;
}