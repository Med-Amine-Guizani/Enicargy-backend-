package org.example.backendenicargy.Dto;


import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString






public class ArticleDTO {
    private Long id ;
    @NotBlank(message = "title musn't be empty")
    private String title ;

    @NotBlank(message = "body musn't be empty also")
    private String body;
}
