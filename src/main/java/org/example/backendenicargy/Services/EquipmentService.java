package org.example.backendenicargy.Services;

import org.example.backendenicargy.Models.Equipment;
import org.example.backendenicargy.Repositories.EquipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import org.example.backendenicargy.Dto.EquipmentDTO;
import org.example.backendenicargy.Models.Equipment;
import org.example.backendenicargy.Repositories.EquipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class EquipmentService {

    @Autowired
    private EquipmentRepository equipmentRepository;

    public List<EquipmentDTO> getAllEquipments() {
        return equipmentRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public EquipmentDTO updateEquipment(Long id, EquipmentDTO dto) {
        Optional<Equipment> equipmentOptional = equipmentRepository.findById(id);
        if (!equipmentOptional.isPresent()) {
            return null;
        }

        Equipment equipment = equipmentOptional.get();
        equipment.setTotal(dto.getTotal());
        equipment.setGood(dto.getBon());
        equipment.setRepair(dto.getEnPanne());
        equipment.setBroken(dto.getEnReparation());
        equipment.setReserve(dto.getReserve());
        equipment.setType(dto.getType());

        Equipment updatedEquipment = equipmentRepository.save(equipment);
        return convertToDTO(updatedEquipment);
    }

    public EquipmentDTO createEquipment(EquipmentDTO dto) {
        Equipment equipment = convertToEntity(dto);
        Equipment savedEquipment = equipmentRepository.save(equipment);
        return convertToDTO(savedEquipment);
    }

    public EquipmentDTO convertToDTO(Equipment equipment) {
        return new EquipmentDTO(
                equipment.getId(),
                equipment.getTotal(),
                equipment.getGood(),
                equipment.getRepair(),
                equipment.getBroken(),
                equipment.getReserve(),
                equipment.getType()
        );
    }

    public Equipment convertToEntity(EquipmentDTO dto) {
        return new Equipment(
                dto.getId(),
                dto.getTotal(),
                dto.getBon(),
                dto.getEnPanne(),
                dto.getEnReparation(),
                dto.getReserve(),
                dto.getType()
        );
    }
}
