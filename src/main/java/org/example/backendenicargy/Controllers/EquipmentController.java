package org.example.backendenicargy.Controllers;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.example.backendenicargy.Dto.EquipmentDTO;
import org.example.backendenicargy.Models.Equipment;
import org.example.backendenicargy.Repositories.EquipmentRepository;
import org.example.backendenicargy.Services.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.example.backendenicargy.Dto.EquipmentDTO;
import org.example.backendenicargy.Services.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/equipments")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
public class EquipmentController {

    @Autowired
    private EquipmentService equipmentService;

    @GetMapping
    public List<EquipmentDTO> getAllEquipments() {
        return equipmentService.getAllEquipments();
    }

    @PutMapping("/{id}")
    public ResponseEntity<EquipmentDTO> updateEquipment(@PathVariable Long id, @RequestBody EquipmentDTO dto) {
        EquipmentDTO updatedEquipment = equipmentService.updateEquipment(id, dto);
        if (updatedEquipment != null) {
            return ResponseEntity.ok(updatedEquipment);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/")
    public ResponseEntity<EquipmentDTO> createEquipment(@RequestBody EquipmentDTO dto) {
        EquipmentDTO createdEquipment = equipmentService.createEquipment(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEquipment);
    }
}
