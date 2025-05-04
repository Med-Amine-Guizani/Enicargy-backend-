package org.example.backendenicargy.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString







@Entity
public class Rapport {
    @Id
    @GeneratedValue
    private Long id ;


    private String title ;



    private String url;

    @CreationTimestamp
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP",updatable = false , insertable = false)
    private String date;

}
