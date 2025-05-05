package org.example.backendenicargy.Controllers;

import org.example.backendenicargy.Dto.EquipmentDTO;
import org.example.backendenicargy.Services.EquipmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class EquipementControllerTest {

    private MockMvc mockMvc;

    @Mock
    private EquipmentService equipmentService;

    @InjectMocks
    private EquipmentController equipmentController;

    private EquipmentDTO equipmentDTO;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(equipmentController).build();
        equipmentDTO = new EquipmentDTO(1L, 10, 8, 1, 1, 2, "Projector");
    }

    @Test
    void getAllEquipments_ShouldReturnListOfEquipmentDTOs() throws Exception {
        // Arrange
        List<EquipmentDTO> equipmentList = Arrays.asList(equipmentDTO);
        when(equipmentService.getAllEquipments()).thenReturn(equipmentList);

        // Act & Assert
        mockMvc.perform(get("/api/equipments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].total").value(10))
                .andExpect(jsonPath("$[0].bon").value(8))
                .andExpect(jsonPath("$[0].enPanne").value(1))
                .andExpect(jsonPath("$[0].enReparation").value(1))
                .andExpect(jsonPath("$[0].reserve").value(2))
                .andExpect(jsonPath("$[0].type").value("Projector"));
    }

    @Test
    void updateEquipment_ShouldReturnUpdatedEquipment_WhenExists() throws Exception {
        // Arrange
        when(equipmentService.updateEquipment(1L, equipmentDTO)).thenReturn(equipmentDTO);

        // Act & Assert
        mockMvc.perform(put("/api/equipments/1")
                        .contentType("application/json")
                        .content("{\"id\":1,\"total\":10,\"bon\":8,\"enPanne\":1,\"enReparation\":1,\"reserve\":2,\"type\":\"Projector\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.total").value(10));
    }

    @Test
    void updateEquipment_ShouldReturnNotFound_WhenNotExists() throws Exception {
        // Arrange
        when(equipmentService.updateEquipment(1L, equipmentDTO)).thenReturn(null);

        // Act & Assert
        mockMvc.perform(put("/api/equipments/1")
                        .contentType("application/json")
                        .content("{\"id\":1,\"total\":10,\"bon\":8,\"enPanne\":1,\"enReparation\":1,\"reserve\":2,\"type\":\"Projector\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createEquipment_ShouldReturnCreatedEquipment() throws Exception {
        // Arrange
        when(equipmentService.createEquipment(any(EquipmentDTO.class))).thenReturn(equipmentDTO);

        // Act & Assert
        mockMvc.perform(post("/api/equipments/")
                        .contentType("application/json")
                        .content("{\"id\":1,\"total\":10,\"bon\":8,\"enPanne\":1,\"enReparation\":1,\"reserve\":2,\"type\":\"Projector\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.total").value(10));
    }
}

