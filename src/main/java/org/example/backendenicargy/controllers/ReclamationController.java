package org.example.backendenicargy.controllers;

import jakarta.validation.Valid;
import org.example.backendenicargy.Reclamation;
import org.example.backendenicargy.dto.ReclamationDTO;
import org.example.backendenicargy.repositories.ReclamationRepository;
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

@RestController
public class ReclamationController {
    //-------------------------------------------------Definitions+Constructor-------------------------------------------------------------------------------------------
    private ReclamationRepository reclamationRepository;



    public ReclamationController(ReclamationRepository reclamationRepository) {
        this.reclamationRepository = reclamationRepository;
    }






    //-----------------------------------------------------Get Route Controllers -------------------------------------------------------------------------------------------------
    @GetMapping("/api/v1/reclamations")
    public List<Reclamation> getrec(){
        return reclamationRepository.findAll();
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
        reclamation.setStatus("En-Attente");
        reclamation.setUserid(dto.getUserid());
        Reclamation saved = reclamationRepository.save(reclamation);
        return ResponseEntity.ok().body(saved);
    }


    @PostMapping("/api/v1/photo/{id}")
    public ResponseEntity<Reclamation> updatePhoto(
        @PathVariable Long id , @RequestParam("file") MultipartFile file
    ){
        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body(null);
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







}
