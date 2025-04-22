package org.example.backendenicargy.Dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    @NotBlank
    private String username;


    @NotBlank
    private String password;


    @NotBlank()
    private String email;


    @NotBlank
    private String role;

}
