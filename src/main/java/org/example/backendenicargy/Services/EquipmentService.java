package org.example.backendenicargy.Services;

import org.example.backendenicargy.Models.Equipment;
import org.example.backendenicargy.Repositories.EquipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipmentService {
    @Autowired
    private EquipmentRepository repo;

    public List<Equipment> getAllEquipments() {
        return repo.findAll();
    }


}
