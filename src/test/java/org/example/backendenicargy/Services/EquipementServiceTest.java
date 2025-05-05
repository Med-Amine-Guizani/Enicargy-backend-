package org.example.backendenicargy.Services;

import org.example.backendenicargy.Dto.EquipmentDTO;
import org.example.backendenicargy.Models.Equipment;
import org.example.backendenicargy.Repositories.EquipmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Transactional
class EquipementServiceTest {

    @Mock
    private EquipmentRepository equipmentRepository;

    @InjectMocks
    private EquipmentService equipmentService;

    private Equipment equipment;
    private EquipmentDTO equipmentDTO;

    @BeforeEach
    void setUp() {
        equipment = new Equipment(1L, 10, 8, 1, 1, 2, "Projector");
        equipmentDTO = new EquipmentDTO(1L, 10, 8, 1, 1, 2, "Projector");
    }

    @Test
    void getAllEquipments_ShouldReturnListOfEquipmentDTOs() {
        // Arrange
        when(equipmentRepository.findAll()).thenReturn(Arrays.asList(equipment));

        // Act
        List<EquipmentDTO> result = equipmentService.getAllEquipments();

        // Assert
        assertEquals(1, result.size());
        assertEquals(equipmentDTO, result.get(0));
        verify(equipmentRepository, times(1)).findAll();
    }

    @Test
    void updateEquipment_ShouldReturnUpdatedDTO_WhenEquipmentExists() {
        // Arrange
        when(equipmentRepository.findById(1L)).thenReturn(Optional.of(equipment));
        when(equipmentRepository.save(any(Equipment.class))).thenReturn(equipment);

        // Act
        EquipmentDTO result = equipmentService.updateEquipment(1L, equipmentDTO);

        // Assert
        assertNotNull(result);
        assertEquals(equipmentDTO, result);
        verify(equipmentRepository, times(1)).findById(1L);
        verify(equipmentRepository, times(1)).save(any(Equipment.class));
    }

    @Test
    void updateEquipment_ShouldReturnNull_WhenEquipmentNotFound() {
        // Arrange
        when(equipmentRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        EquipmentDTO result = equipmentService.updateEquipment(1L, equipmentDTO);

        // Assert
        assertNull(result);
        verify(equipmentRepository, times(1)).findById(1L);
        verify(equipmentRepository, never()).save(any(Equipment.class));
    }

    @Test
    void createEquipment_ShouldReturnCreatedDTO() {
        // Arrange
        when(equipmentRepository.save(any(Equipment.class))).thenReturn(equipment);

        // Act
        EquipmentDTO result = equipmentService.createEquipment(equipmentDTO);

        // Assert
        assertNotNull(result);
        assertEquals(equipmentDTO, result);
        verify(equipmentRepository, times(1)).save(any(Equipment.class));
    }

    @Test
    void convertToDTO_ShouldReturnCorrectDTO() {
        // Act
        EquipmentDTO result = equipmentService.convertToDTO(equipment);

        // Assert
        assertEquals(equipment.getId(), result.getId());
        assertEquals(equipment.getTotal(), result.getTotal());
        assertEquals(equipment.getGood(), result.getBon());
        assertEquals(equipment.getRepair(), result.getEnPanne());
        assertEquals(equipment.getBroken(), result.getEnReparation());
        assertEquals(equipment.getReserve(), result.getReserve());
        assertEquals(equipment.getType(), result.getType());
    }

    @Test
    void convertToEntity_ShouldReturnCorrectEntity() {
        // Act
        Equipment result = equipmentService.convertToEntity(equipmentDTO);

        // Assert
        assertEquals(equipmentDTO.getId(), result.getId());
        assertEquals(equipmentDTO.getTotal(), result.getTotal());
        assertEquals(equipmentDTO.getBon(), result.getGood());
        assertEquals(equipmentDTO.getEnPanne(), result.getRepair());
        assertEquals(equipmentDTO.getEnReparation(), result.getBroken());
        assertEquals(equipmentDTO.getReserve(), result.getReserve());
        assertEquals(equipmentDTO.getType(), result.getType());
    }
}