package org.example.backendenicargy.Controllers;

import jakarta.validation.Valid;
import org.example.backendenicargy.Dto.ReclamationStatusUserDTO;
import org.example.backendenicargy.Models.Reclamation;
import org.example.backendenicargy.Dto.ReclamationDTO;
import org.example.backendenicargy.Dto.ReclamationStatusDTO;
import org.example.backendenicargy.Models.User;
import org.example.backendenicargy.Repositories.ReclamationRepository;
import org.example.backendenicargy.Services.ReclamationService;
import org.hibernate.boot.jaxb.SourceType;
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
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


@CrossOrigin(origins = "http://localhost:4200" , allowedHeaders = "*")
@RestController
public class ReclamationController {
    //-------------------------------------------------Definitions+Constructor-------------------------------------------------------------------------------------------
    private ReclamationRepository reclamationRepository;
    @Autowired
    private ReclamationService reclamationService;
    public ReclamationController(ReclamationRepository reclamationRepository) {
        this.reclamationRepository = reclamationRepository;
    }


    //-----------------------------------------------------Get Route Controllers -------------------------------------------------------------------------------------------------
    @GetMapping("/api/v1/reclamations")
    public List<Reclamation> getrec(){

        return reclamationRepository.findAll();
    }


    @GetMapping("/api/v1/reclamation/stats/{userid}")
    public ResponseEntity<ReclamationStatusDTO> getStatusCountsByUser(@PathVariable Long userId) {
        long enAttenteCount = reclamationRepository.countByUser_idAndStatus(userId, "En_Attente");
        long enCoursCount = reclamationRepository.countByUser_idAndStatus(userId, "En_cours");
        long terminerCount = reclamationRepository.countByUser_idAndStatus(userId, "Terminer");

        ReclamationStatusDTO result = new ReclamationStatusDTO(enAttenteCount, enCoursCount, terminerCount);
        return ResponseEntity.ok(result);
    }

    //----------------------------------------------------------Post Route Controllers --------------------------------------------------------------------------------------------------
    //this is a post method which allows users to insert reclamation into database only with title desc salle loc and userID
    @PostMapping("/api/v1/reclamations")
    public ResponseEntity<Reclamation> createRec(
            @Valid @RequestBody ReclamationDTO dto , BindingResult bindingResult
    ){

        //si body manque quellque chose tres importante request fails  faillite
        if(bindingResult.hasErrors()){
            System.out.println(bindingResult.getAllErrors());
            return ResponseEntity.badRequest().body(null);
        }


        Reclamation reclamation = new Reclamation();
        reclamation.setTitre(dto.getTitre());
        reclamation.setDescription(dto.getDescription());
        reclamation.setLocal(dto.getLocal());
        reclamation.setSalle(dto.getSalle());
        reclamation.setStatus("En_Attente");


        Optional<User> opUser=reclamationService.userForRec(dto.getUserid());
        if(opUser.isPresent()){
            User user = opUser.get();
            reclamation.setUser(user);
        }else{
            ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
        }



        Reclamation saved = reclamationRepository.save(reclamation);
        return ResponseEntity.ok().body(saved);
    }

    @PostMapping("/api/v1/photo/{id}")
    public ResponseEntity<Reclamation> updatePhoto(
        @PathVariable Long id , @RequestParam("file") MultipartFile file
    ){
        try {
            if (file.isEmpty()) {

            }
        String uploadDir = "uploads/";
        File dir = new File(uploadDir);
        if(!dir.exists()){dir.mkdirs();}


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
        }catch(IOException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

    }















































    //---------------------------------------------- Delete methode Route --------------------------------------------------------------------------------------

    @DeleteMapping("api/v1/reclamations/{id}")
    public ResponseEntity<String> deleteReclamation(@PathVariable Long id) {
        Optional<Reclamation> optional = reclamationRepository.findById(id);

        if (optional.isPresent()) {
            Reclamation reclamation = optional.get();
            // Delete the associated photo if it exists
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


























    //-------------------------------------------Patch methodes-------------------------------------------------------------------
    @PatchMapping("api/v1/reclamations/status/{id}")
    public ResponseEntity<String> advanceReclamationStatus(@PathVariable Long id) {
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
                    return ResponseEntity.ok("Reclamation already completed.");
                default:
                    return ResponseEntity.badRequest().body("Invalid current status: " + currentStatus);
            }

            reclamationRepository.save(reclamation);
            return ResponseEntity.ok("Status updated to: " + reclamation.getStatus());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Reclamation with ID " + id + " not found");
        }
    }

    @GetMapping("api/v1/reclamations/user/{userId}")
    public List<ReclamationStatusUserDTO> getReclamationsByUser(@PathVariable Long userId) {
        List<Reclamation> reclamations = reclamationRepository.findByUserId(userId);

        return reclamations.stream()
                .map(rec -> new ReclamationStatusUserDTO(rec.getDate(), rec.getStatus(), rec.getTitre()))
                .collect(Collectors.toList());
    }












}
