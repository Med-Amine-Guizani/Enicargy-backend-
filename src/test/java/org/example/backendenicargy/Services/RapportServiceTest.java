package org.example.backendenicargy.Services;

import org.example.backendenicargy.Models.Rapport;
import org.example.backendenicargy.Repositories.RapportRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RapportServiceTest {

    @Mock
    private RapportRepository rapportRepository;

    @InjectMocks
    private RapportService rapportService;

    private Rapport rapport;
    private MultipartFile testFile;

    @BeforeEach
    void setUp() throws IOException {
        rapport = new Rapport(1L, "Test Report", "test.pdf", "2023-01-01");
        testFile = new MockMultipartFile("file", "test.pdf", "application/pdf", "test content".getBytes());
    }

    @Test
    void getRapports_ShouldReturnAllRapports() {
        // Arrange
        when(rapportRepository.findAll()).thenReturn(Arrays.asList(rapport));

        // Act
        List<Rapport> result = rapportService.getRapports();

        // Assert
        assertEquals(1, result.size());
        assertEquals(rapport, result.get(0));
        verify(rapportRepository, times(1)).findAll();
    }

    @Test
    void addRapport_ShouldSaveRapportAndFile() throws IOException {
        // Arrange
        when(rapportRepository.save(any(Rapport.class))).thenReturn(rapport);

        // Act
        Rapport result = rapportService.addRapport("Test Report", testFile);

        // Assert
        assertNotNull(result);
        assertEquals("Test Report", result.getTitle());
        assertNotNull(result.getUrl());
        verify(rapportRepository, times(2)).save(any(Rapport.class));

        // Cleanup
        Files.deleteIfExists(Path.of("uploads/" + result.getUrl()));
    }

    @Test
    void addRapport_ShouldReturnNull_WhenFileUploadFails() {

        try{
            // Arrange
            MultipartFile invalidFile = mock(MultipartFile.class);
            when(invalidFile.getOriginalFilename()).thenReturn("test.pdf");
            when(rapportRepository.save(any(Rapport.class))).thenReturn(rapport);
            doThrow(new RuntimeException("File upload failed")).when(invalidFile).transferTo(any(Path.class));

            // Act
            Rapport result = rapportService.addRapport("Test Report", invalidFile);

            // Assert
            assertNull(result);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    void deleteRapport_ShouldDeleteRapportAndFile() throws IOException {
        // Arrange
        when(rapportRepository.findById(1L)).thenReturn(java.util.Optional.of(rapport));

        // Create a test file to delete
        Path testFilePath = Path.of("uploads/test.pdf");
        Files.createDirectories(testFilePath.getParent());
        Files.write(testFilePath, "test content".getBytes());

        // Act
        rapportService.deleteRapport(1L);

        // Assert
        verify(rapportRepository, times(1)).delete(rapport);
        assertFalse(Files.exists(testFilePath));
    }
}
