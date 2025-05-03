package org.example.backendenicargy.Controllers;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.example.backendenicargy.Dto.RapportDTO;
import org.example.backendenicargy.Models.Rapport;
import org.example.backendenicargy.Services.RapportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@CrossOrigin(origins = "http://localhost:4200" , allowedHeaders = "*")
@RestController
@RequestMapping("/api/v1/rapports")
public class RapportController {

    @Autowired
    private RapportService rapportService;

    @GetMapping("")
    public List<Rapport> getRapprots(){
        return rapportService.getRapports();
    }


    @PostMapping("")
    public Rapport createRapport(
            @RequestPart("title") String title , @RequestPart("file") MultipartFile file
    ){
        return rapportService.addRapport(title,file);
    }

    @DeleteMapping("/{id}")
    public void deleteRapport(@NotNull @PathVariable Long id){
        rapportService.deleteRapport(id);
    }


}
