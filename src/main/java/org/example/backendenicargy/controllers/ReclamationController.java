package org.example.backendenicargy.controllers;

import jakarta.validation.Valid;
import org.example.backendenicargy.Reclamation;
import org.example.backendenicargy.dto.ReclamationDTO;
import org.example.backendenicargy.repositories.ReclamationRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
public class ReclamationController {
    private ReclamationRepository reclamationRepository;


    public ReclamationController(ReclamationRepository reclamationRepository) {
        this.reclamationRepository = reclamationRepository;
    }

    @GetMapping("/api/v1/reclamations")
    public List<Reclamation> getrec(){
        return reclamationRepository.findAll();
    }




    //this is a post method which allows users to insert reclamation into database only with title desc salle loc and userID
    @PostMapping("/api/v1/reclamations")
    public ResponseEntity<Reclamation> createRec(
            @Valid @RequestBody ReclamationDTO dto , BindingResult bindingResult
    ){

        //body manque quellque chose tres importante
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






}
