package org.example.backendenicargy.Models;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString






@Entity
public class Article {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id ;

    private String title ;


    @Lob
    @Column(columnDefinition = "MEDIUMTEXT")
    private String body;


    @CreationTimestamp
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP",updatable = false , insertable = false)
    private String date;


}
