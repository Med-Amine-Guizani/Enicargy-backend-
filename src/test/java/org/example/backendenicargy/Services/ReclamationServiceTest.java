package org.example.backendenicargy.Services;

import jakarta.validation.constraints.Null;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import org.example.backendenicargy.Dto.*;
import org.example.backendenicargy.Models.Reclamation;
import org.example.backendenicargy.Models.User;
import org.example.backendenicargy.Repositories.ReclamationRepository;
import org.example.backendenicargy.Repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReclamationServiceTest {

    @Mock
    private ReclamationRepository reclamationRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private MultipartFile file;

    @InjectMocks
    private ReclamationService reclamationService;

    private Reclamation reclamation;
    private ReclamationDTO reclamationDTO;
    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);

        reclamation = new Reclamation();
        reclamation.setId(1L);
        reclamation.setTitre("Test Title");
        reclamation.setDescription("Test Description");
        reclamation.setLocal("Test Local");
        reclamation.setSalle("Test Salle");
        reclamation.setStatus("En_Attente");
        reclamation.setUser(user);

        reclamationDTO = new ReclamationDTO();
        reclamationDTO.setTitre("Test Title");
        reclamationDTO.setDescription("Test Description");
        reclamationDTO.setLocal("Test Local");
        reclamationDTO.setSalle("Test Salle");
        reclamationDTO.setUserid(1L);
    }

    @Test
    void getAllReclamations_ShouldReturnAllReclamations() {
        // Arrange
        when(reclamationRepository.findAll()).thenReturn(Arrays.asList(reclamation));

        // Act
        List<Reclamation> result = reclamationService.getAllReclamations();

        // Assert
        assertEquals(1, result.size());
        assertEquals(reclamation, result.get(0));
        verify(reclamationRepository, times(1)).findAll();
    }

    @Test
    void getStatusCountsByUser_ShouldReturnStats_WhenUserExists() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(reclamationRepository.countByUser_idAndStatus(1L, "En_Attente")).thenReturn(1L);
        when(reclamationRepository.countByUser_idAndStatus(1L, "En_cours")).thenReturn(2L);
        when(reclamationRepository.countByUser_idAndStatus(1L, "Terminer")).thenReturn(3L);

        // Act
        ResponseEntity<ReclamationStatusDTO> response = reclamationService.getStatusCountsByUser(1L);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1L, response.getBody().getEn_attente());
        assertEquals(2L, response.getBody().getEn_cours());
        assertEquals(3L, response.getBody().getTerminer());
    }

    @Test
    void getStatusCountsByUser_ShouldReturnNotFound_WhenUserNotExists() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<ReclamationStatusDTO> response = reclamationService.getStatusCountsByUser(1L);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void createReclamation_ShouldCreateNewReclamation_WhenValidInput() {
        // Arrange
        when(bindingResult.hasErrors()).thenReturn(false);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(reclamationRepository.save(any(Reclamation.class))).thenReturn(reclamation);

        // Act
        ResponseEntity<Reclamation> response = reclamationService.createReclamation(reclamationDTO, bindingResult);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(reclamation, response.getBody());
    }

    @Test
    void createReclamation_ShouldReturnBadRequest_WhenBindingErrors() {
        // Arrange
        when(bindingResult.hasErrors()).thenReturn(true);

        // Act
        ResponseEntity<Reclamation> response = reclamationService.createReclamation(reclamationDTO, bindingResult);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void createReclamation_ShouldReturnExpectationFailed_WhenUserNotFound() {
        // Arrange
        when(bindingResult.hasErrors()).thenReturn(false);
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Reclamation> response = reclamationService.createReclamation(reclamationDTO, bindingResult);

        // Assert
        assertEquals(HttpStatus.EXPECTATION_FAILED, response.getStatusCode());
    }

    @Test
    void updatePhoto_ShouldUpdatePhoto_WhenValidInput() throws IOException {
        // Arrange
        when(file.isEmpty()).thenReturn(false);
        when(reclamationRepository.findById(1L)).thenReturn(Optional.of(reclamation));
        when(reclamationRepository.save(any(Reclamation.class))).thenReturn(reclamation);

        // Act
        ResponseEntity<Reclamation> response = reclamationService.updatePhoto(1L, file);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(reclamation, response.getBody());
    }

    @Test
    void deleteReclamation_ShouldDelete_WhenReclamationExists() {
        // Arrange
        when(reclamationRepository.findById(1L)).thenReturn(Optional.of(reclamation));

        // Act
        ResponseEntity<String> response = reclamationService.deleteReclamation(1L);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(reclamationRepository, times(1)).deleteById(1L);
    }

    @Test
    void advanceReclamationStatus_ShouldAdvanceStatus() {
        // Arrange
        when(reclamationRepository.findById(1L)).thenReturn(Optional.of(reclamation));
        when(reclamationRepository.save(any(Reclamation.class))).thenReturn(reclamation);

        // Act - Initial status is En_Attente
        ResponseEntity<Null> response = reclamationService.advanceReclamationStatus(1L);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("En_cours", reclamation.getStatus());
    }
}
