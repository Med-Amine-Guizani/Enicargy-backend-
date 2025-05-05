package org.example.backendenicargy.Controllers;

import org.example.backendenicargy.Dto.*;
import org.example.backendenicargy.Models.Reclamation;
import org.example.backendenicargy.Services.ReclamationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;








@ExtendWith(MockitoExtension.class)
class ReclamationControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ReclamationService reclamationService;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private ReclamationController reclamationController;

    private Reclamation reclamation;
    private ReclamationDTO reclamationDTO;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(reclamationController).build();

        reclamation = new Reclamation();
        reclamation.setId(1L);
        reclamation.setTitre("Test Title");

        reclamationDTO = new ReclamationDTO();
        reclamationDTO.setTitre("Test Title");
    }

    @Test
    void getAllReclamations_ShouldReturnReclamations() throws Exception {
        // Arrange
        when(reclamationService.getAllReclamations()).thenReturn(Arrays.asList(reclamation));

        // Act & Assert
        mockMvc.perform(get("/api/v1/reclamations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].titre").value("Test Title"));
    }

    @Test
    void createReclamation_ShouldReturnCreatedReclamation() throws Exception {
        // Arrange
        when(reclamationService.createReclamation(any(ReclamationDTO.class), any(BindingResult.class)))
                .thenReturn(ResponseEntity.ok(reclamation));

        // Act & Assert
        mockMvc.perform(post("/api/v1/reclamations")
                        .contentType("application/json")
                        .content("{\"titre\":\"Test Title\",\"description\":\"Test\",\"local\":\"Test\",\"salle\":\"Test\",\"userid\":1}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.titre").value("Test Title"));
    }

    @Test
    void updatePhoto_ShouldUpdatePhoto() throws Exception {
        // Arrange
        MockMultipartFile file = new MockMultipartFile("file", "test.jpg", "image/jpeg", "test data".getBytes());
        when(reclamationService.updatePhoto(1L, file)).thenReturn(ResponseEntity.ok(reclamation));

        // Act & Assert
        mockMvc.perform(multipart("/api/v1/photo/1")
                        .file(file))
                .andExpect(status().isOk());
    }

    @Test
    void deleteReclamation_ShouldDelete() throws Exception {
        // Arrange
        when(reclamationService.deleteReclamation(1L))
                .thenReturn(ResponseEntity.ok("Deleted"));

        // Act & Assert
        mockMvc.perform(delete("/api/v1/reclamations/1"))
                .andExpect(status().isOk());
    }
}
