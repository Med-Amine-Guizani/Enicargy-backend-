package org.example.backendenicargy.Models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


class ReclamationTest {

    @Test
    void testReclamationEntity() {
        // Arrange
        Reclamation reclamation = new Reclamation();
        reclamation.setId(1L);
        reclamation.setTitre("Test Title");
        reclamation.setDescription("Test Description");
        reclamation.setLocal("Test Local");
        reclamation.setSalle("Test Salle");
        reclamation.setStatus("En_Attente");
        reclamation.setPhotourl("photo.jpg");

        User user = new User();
        user.setId(1L);
        reclamation.setUser(user);

        // Act & Assert
        assertEquals(1L, reclamation.getId());
        assertEquals("Test Title", reclamation.getTitre());
        assertEquals("Test Description", reclamation.getDescription());
        assertEquals("Test Local", reclamation.getLocal());
        assertEquals("Test Salle", reclamation.getSalle());
        assertEquals("En_Attente", reclamation.getStatus());
        assertEquals("photo.jpg", reclamation.getPhotourl());
        assertEquals(user, reclamation.getUser());
        assertNotNull(reclamation.getDate()); // Should be set by @CreationTimestamp
    }

}
