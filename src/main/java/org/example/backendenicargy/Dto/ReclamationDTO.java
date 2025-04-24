package org.example.backendenicargy.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString


public class ReclamationDTO {
    @NotBlank
    private String titre;

    @NotBlank
    private String description;

    @NotBlank
    private String local;

    @NotBlank
    private String salle;


    @NotNull
    private Long userid;


}
