package org.example.backendenicargy.Controllers;

import org.example.backendenicargy.Models.Rapport;
import org.example.backendenicargy.Services.RapportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class RapportControllerTest {

    private MockMvc mockMvc;

    @Mock
    private RapportService rapportService;

    @InjectMocks
    private RapportController rapportController;

    private Rapport rapport;
    private MockMultipartFile testFile;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(rapportController).build();
        rapport = new Rapport(1L, "Test Report", "test.pdf", "2023-01-01");
        testFile = new MockMultipartFile("file", "test.pdf", "application/pdf", "test content".getBytes());
    }

    @Test
    void getRapports_ShouldReturnListOfRapports() throws Exception {
        // Arrange
        List<Rapport> rapports = Arrays.asList(rapport);
        when(rapportService.getRapports()).thenReturn(rapports);

        // Act & Assert
        mockMvc.perform(get("/api/v1/rapports"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].title").value("Test Report"))
                .andExpect(jsonPath("$[0].url").value("test.pdf"));
    }

    @Test
    void createRapport_ShouldCreateNewRapport() throws Exception {
        // Arrange
        when(rapportService.addRapport("Test Report", testFile)).thenReturn(rapport);

        // Act & Assert
        mockMvc.perform(multipart("/api/v1/rapports")
                        .file(testFile)
                        .param("title", "Test Report"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Test Report"));
    }

    @Test
    void deleteRapport_ShouldDeleteRapport() throws Exception {
        // Arrange - no return needed for void method

        // Act & Assert
        mockMvc.perform(delete("/api/v1/rapports/1"))
                .andExpect(status().isOk());

        verify(rapportService, times(1)).deleteRapport(1L);
    }
}
