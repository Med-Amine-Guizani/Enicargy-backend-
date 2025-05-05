package org.example.backendenicargy.Dto;




import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EquipmentDTO {
    private Long id;

    private int total;
    private int bon;
    private int enPanne;
    private int enReparation;
    private int reserve;
    private String type;
}

