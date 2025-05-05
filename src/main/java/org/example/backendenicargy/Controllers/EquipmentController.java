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

@RestController
@RequestMapping("/api/equipments")
@CrossOrigin(origins = "http://localhost:4200" , allowedHeaders = "*")
public class EquipmentController {

    @Autowired
    private EquipmentService service;
    @Autowired
    private EquipmentRepository equipmentRepository; // Ajout de l'injection de EquipmentRepository

    @GetMapping
    public List<EquipmentDTO> getEquipments() {
        return service.getAllEquipments()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    public ResponseEntity<EquipmentDTO> updateEquipment(@PathVariable Long id, @RequestBody EquipmentDTO dto) {
        Optional<Equipment> equipmentOptional = equipmentRepository.findById(id); // Utiliser l'instance injectée de EquipmentRepository
        if (!equipmentOptional.isPresent()) {
            return ResponseEntity.notFound().build(); // Retourne 404 si l'équipement n'est pas trouvé
        }

        Equipment equipment = equipmentOptional.get();

        // Mettre à jour les champs de l'équipement à partir du DTO
        equipment.setTotal(dto.getTotal());
        equipment.setGood(dto.getBon());
        equipment.setRepair(dto.getEnPanne());
        equipment.setBroken(dto.getEnReparation());
        equipment.setReserve(dto.getReserve());
        // Autres champs à mettre à jour si nécessaire

        // Sauvegarder l'équipement mis à jour
        equipmentRepository.save(equipment); // Utiliser l'instance injectée de EquipmentRepository

        // Convertir l'entité mise à jour en DTO et la retourner dans la réponse
        EquipmentDTO updatedDTO = convertToDTO(equipment);

        return ResponseEntity.ok(updatedDTO); // Retourne l'équipement mis à jour en DTO
    }



    // === Méthodes de conversion ===
    private EquipmentDTO convertToDTO(Equipment e) {
        return new EquipmentDTO(e.getId(),e.getTotal(), e.getGood(), e.getRepair(), e.getBroken(), e.getReserve(),e.getType());
    }

    private Equipment convertToEntity(EquipmentDTO dto) {
        return new Equipment(dto.getId(),  dto.getTotal(), dto.getBon(), dto.getEnPanne(), dto.getEnReparation(), dto.getReserve(),dto.getType());
    }
    @PostMapping("/")
    public ResponseEntity<EquipmentDTO> createEquipment(@RequestBody EquipmentDTO dto) {
        // Convertir le DTO en entité Equipment
        Equipment equipment = convertToEntity(dto);

        // Sauvegarder l'équipement dans la base de données
        equipmentRepository.save(equipment);

        // Convertir l'entité Equipment en DTO
        EquipmentDTO createdDTO = convertToDTO(equipment);

        // Retourner la réponse avec le DTO de l'équipement créé
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDTO); // Code 201 pour 'Created'
    }
}
