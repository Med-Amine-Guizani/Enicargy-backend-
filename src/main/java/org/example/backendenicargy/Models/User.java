package org.example.backendenicargy.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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

    @OneToMany(mappedBy = "user" , cascade = CascadeType.ALL , orphanRemoval=true)
    @JsonIgnore
    private List<Reclamation> reclamationList;




}
