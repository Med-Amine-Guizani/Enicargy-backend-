package org.example.backendenicargy.Models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RapportTest {

    @Test
    void testRapportEntity() {
        // Arrange
        Rapport rapport = new Rapport();
        rapport.setId(1L);
        rapport.setTitle("Monthly Report");
        rapport.setUrl("reports/monthly.pdf");

        // Assert
        assertEquals(1L, rapport.getId());
        assertEquals("Monthly Report", rapport.getTitle());
        assertEquals("reports/monthly.pdf", rapport.getUrl());
        assertNotNull(rapport.getDate()); // Should be set by @CreationTimestamp
    }

    @Test
    void testAllArgsConstructor() {
        // Arrange & Act
        Rapport rapport = new Rapport(1L, "Annual Report", "reports/annual.pdf", "2023-01-01");

        // Assert
        assertEquals(1L, rapport.getId());
        assertEquals("Annual Report", rapport.getTitle());
        assertEquals("reports/annual.pdf", rapport.getUrl());
        assertEquals("2023-01-01", rapport.getDate());
    }
}