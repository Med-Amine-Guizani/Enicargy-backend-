package org.example.backendenicargy.Models;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

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

    private String body ;


}
