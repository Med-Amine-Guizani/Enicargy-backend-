package org.example.backendenicargy.Models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EquipementTest {

    @Test
    void testEquipmentEntity() {
        // Arrange
        Equipment equipment = new Equipment();
        equipment.setId(1L);
        equipment.setTotal(10);
        equipment.setGood(8);
        equipment.setRepair(1);
        equipment.setBroken(1);
        equipment.setReserve(2);
        equipment.setType("Projector");

        // Assert
        assertEquals(1L, equipment.getId());
        assertEquals(10, equipment.getTotal());
        assertEquals(8, equipment.getGood());
        assertEquals(1, equipment.getRepair());
        assertEquals(1, equipment.getBroken());
        assertEquals(2, equipment.getReserve());
        assertEquals("Projector", equipment.getType());
    }

    @Test
    void testAllArgsConstructor() {
        // Arrange & Act
        Equipment equipment = new Equipment(1L, 10, 8, 1, 1, 2, "Projector");

        // Assert
        assertEquals(1L, equipment.getId());
        assertEquals(10, equipment.getTotal());
        assertEquals(8, equipment.getGood());
        assertEquals(1, equipment.getRepair());
        assertEquals(1, equipment.getBroken());
        assertEquals(2, equipment.getReserve());
        assertEquals("Projector", equipment.getType());
    }
}
