package org.example.backendenicargy.Controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Null;
import org.example.backendenicargy.Dto.*;
import org.example.backendenicargy.Models.Reclamation;
import org.example.backendenicargy.Models.User;
import org.example.backendenicargy.Repositories.ReclamationRepository;
import org.example.backendenicargy.Repositories.UserRepository;
import org.example.backendenicargy.Services.ReclamationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.stream.Collectors;


@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
@RestController
public class ReclamationController {

    @Autowired
    private ReclamationService reclamationService;

    @GetMapping("/api/v1/reclamations")
    public List<Reclamation> getrec() {
        return reclamationService.getAllReclamations();
    }

    @GetMapping("/api/v1/reclamation/stats/{userId}")
    public ResponseEntity<ReclamationStatusDTO> getStatusCountsByUser(@PathVariable Long userId) {
        return reclamationService.getStatusCountsByUser(userId);
    }

    @GetMapping("/api/v1/reclamation/reclamation-by-month")
    public ResponseEntity<List<ReclamationByMonthDTO>> getReclamationByMonth() {
        return reclamationService.getReclamationByMonth();
    }

    @GetMapping("/api/v1/reclamation/status-by-month")
    public ResponseEntity<List<ReclamationStatusMonthDTO>> getStatusByMonth() {
        return reclamationService.getStatusByMonth();
    }

    @GetMapping("/api/v1/reclamation/statusUser")
    public ResponseEntity<ReclamationByRoleDTO> getReclamationCountByRole() {
        return reclamationService.getReclamationCountByRole();
    }

    @PostMapping("/api/v1/reclamations")
    public ResponseEntity<Reclamation> createRec(
            @Valid @RequestBody ReclamationDTO dto, BindingResult bindingResult) {
        return reclamationService.createReclamation(dto, bindingResult);
    }

    @PostMapping("/api/v1/photo/{id}")
    public ResponseEntity<Reclamation> updatePhoto(
            @PathVariable Long id, @RequestParam("file") MultipartFile file) {
        return reclamationService.updatePhoto(id, file);
    }

    @DeleteMapping("api/v1/reclamations/{id}")
    public ResponseEntity<String> deleteReclamation(@PathVariable Long id) {
        return reclamationService.deleteReclamation(id);
    }

    @PatchMapping("api/v1/reclamations/status/{id}")
    public ResponseEntity<Null> advanceReclamationStatus(@PathVariable Long id) {
        return reclamationService.advanceReclamationStatus(id);
    }

    @GetMapping("api/v1/reclamations/user/{userId}")
    public List<ReclamationStatusUserDTO> getReclamationsByUser(@PathVariable Long userId) {
        return reclamationService.getReclamationsByUser(userId);
    }
}
