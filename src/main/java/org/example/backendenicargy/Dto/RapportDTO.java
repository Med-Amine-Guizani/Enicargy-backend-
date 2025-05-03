package org.example.backendenicargy.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.File;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString






public class RapportDTO {

    private Long id ;

    @NotBlank
    private String title ;

    private String url ;


    private String date;
}
