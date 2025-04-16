package org.example.backendenicargy.Models;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString



@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;



    @Column(insertable = true ,nullable = false )
    private String userName;


    @Column(unique=true, nullable=false)
    private String email;

    @Column(nullable=false)
    private String password;


    @Column(nullable=false)
    private String role ; //admin or other


}
