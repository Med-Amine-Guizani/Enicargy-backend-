package org.example.backendenicargy.Services;


import jakarta.validation.Valid;
import jakarta.validation.constraints.Null;
import org.example.backendenicargy.Dto.*;
import org.example.backendenicargy.Models.Reclamation;
import org.example.backendenicargy.Models.User;
import org.example.backendenicargy.Repositories.ReclamationRepository;
import org.example.backendenicargy.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.ServletWebServerFactoryAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ReclamationService {

    private static final String[] MOIS_LABELS = {
            "Janvier", "Février", "Mars", "Avril", "Mai", "Juin",
            "Juillet", "Août", "Septembre", "Octobre", "Novembre", "Décembre"
    };

    @Autowired
    private ReclamationRepository reclamationRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Reclamation> getAllReclamations() {
        return reclamationRepository.findAll();
    }

    public ResponseEntity<ReclamationStatusDTO> getStatusCountsByUser(Long userId) {
        if (userRepository.findById(userId).isPresent()) {
            long enAttenteCount = reclamationRepository.countByUser_idAndStatus(userId, "En_Attente");
            long enCoursCount = reclamationRepository.countByUser_idAndStatus(userId, "En_cours");
            long terminerCount = reclamationRepository.countByUser_idAndStatus(userId, "Terminer");
            ReclamationStatusDTO result = new ReclamationStatusDTO(enAttenteCount, enCoursCount, terminerCount);
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<List<ReclamationByMonthDTO>> getReclamationByMonth() {
        List<ReclamationRepository.MonthlyReclamationStats> stats =
                reclamationRepository.countMonthlyStats();

        List<ReclamationByMonthDTO> result = new ArrayList<>();
        for (ReclamationRepository.MonthlyReclamationStats s : stats) {
            String moisNom = MOIS_LABELS[s.getMois() - 1];
            result.add(new ReclamationByMonthDTO(
                    moisNom,
                    s.getTotal(),
                    s.getTerminees()
            ));
        }
        return ResponseEntity.ok(result);
    }

    public ResponseEntity<List<ReclamationStatusMonthDTO>> getStatusByMonth() {
        List<ReclamationRepository.MonthlyReclamationStatus> stats =
                reclamationRepository.countMonthlyStatus();

        List<ReclamationStatusMonthDTO> result = new ArrayList<>();
        for (ReclamationRepository.MonthlyReclamationStatus s : stats) {
            String moisNom = MOIS_LABELS[s.getMois() - 1];
            result.add(new ReclamationStatusMonthDTO(
                    moisNom,
                    s.getEnAttente(),
                    s.getTerminees(),
                    s.getEnCours()
            ));
        }
        return ResponseEntity.ok(result);
    }

    public ResponseEntity<ReclamationByRoleDTO> getReclamationCountByRole() {
        long etudiantCount = reclamationRepository.countByUserRole("Etudiant");
        long enseigneantCount = reclamationRepository.countByUserRole("Enseigneant");
        long personnelCount = reclamationRepository.countByUserRole("Personnel");

        ReclamationByRoleDTO result = new ReclamationByRoleDTO(etudiantCount, enseigneantCount, personnelCount);
        return ResponseEntity.ok(result);
    }

    public ResponseEntity<Reclamation> createReclamation(@Valid ReclamationDTO dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getAllErrors());
            return ResponseEntity.badRequest().body(null);
        }

        Reclamation reclamation = new Reclamation();
        reclamation.setTitre(dto.getTitre());
        reclamation.setDescription(dto.getDescription());
        reclamation.setLocal(dto.getLocal());
        reclamation.setSalle(dto.getSalle());
        reclamation.setStatus("En_Attente");

        Optional<User> opUser = userForRec(dto.getUserid());
        if (opUser.isPresent()) {
            User user = opUser.get();
            reclamation.setUser(user);
        } else {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
        }

        Reclamation saved = reclamationRepository.save(reclamation);
        return ResponseEntity.ok().body(saved);
    }

    public ResponseEntity<Reclamation> updatePhoto(Long id, MultipartFile file) {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body(null);
            }
            String uploadDir = "uploads/";
            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(uploadDir, filename);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            Optional<Reclamation> optional = reclamationRepository.findById(id);
            if (optional.isPresent()) {
                Reclamation reclamation = optional.get();
                reclamation.setPhotourl(filename);
                Reclamation saved = reclamationRepository.save(reclamation);
                return ResponseEntity.ok().body(saved);
            }
            return ResponseEntity.badRequest().body(null);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    public ResponseEntity<String> deleteReclamation(Long id) {
        Optional<Reclamation> optional = reclamationRepository.findById(id);

        if (optional.isPresent()) {
            Reclamation reclamation = optional.get();
            if (reclamation.getPhotourl() != null) {
                File photoFile = new File("uploads/" + reclamation.getPhotourl());
                if (photoFile.exists()) {
                    boolean deleted = photoFile.delete();
                    System.out.println("Photo deleted: " + deleted);
                }
            }

            reclamationRepository.deleteById(id);
            return ResponseEntity.ok("Reclamation and associated photo deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Reclamation with ID " + id + " not found");
        }
    }

    public ResponseEntity<Null> advanceReclamationStatus(Long id) {
        Optional<Reclamation> optional = reclamationRepository.findById(id);

        if (optional.isPresent()) {
            Reclamation reclamation = optional.get();
            String currentStatus = reclamation.getStatus();

            switch (currentStatus) {
                case "En_Attente":
                    reclamation.setStatus("En_cours");
                    break;
                case "En_cours":
                    reclamation.setStatus("Terminer");
                    break;
                case "Terminer":
                    return ResponseEntity.ok(null);
                default:
                    return ResponseEntity.badRequest().body(null);
            }

            reclamationRepository.save(reclamation);
            return ResponseEntity.ok(null);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }



    public List<ReclamationStatusUserDTO> getReclamationsByUser(Long userId) {
        List<Reclamation> reclamations = reclamationRepository.findByUserId(userId);
        return reclamations.stream()
                .map(rec -> new ReclamationStatusUserDTO(rec.getDate(), rec.getStatus(), rec.getTitre()))
                .collect(Collectors.toList());
    }

    public Optional<User> userForRec(Long id) {
        return userRepository.findById(id);
    }


    public int userCount(){
        return userRepository.findAll().size();
    }
}
