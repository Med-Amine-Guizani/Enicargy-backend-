package org.example.backendenicargy.Services;


import org.example.backendenicargy.Dto.RapportDTO;
import org.example.backendenicargy.Models.Rapport;
import org.example.backendenicargy.Repositories.RapportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;


@Service
public class RapportService {

    @Autowired
    private RapportRepository rapportRepository;


    public List<Rapport> getRapports(){
         return rapportRepository.findAll();
    }


    public Rapport addRapport (String title , MultipartFile file ){
         Rapport rapport = new Rapport();
         rapport.setTitle(title);
         Rapport newRapport =  rapportRepository.save(rapport);
         try {
             String uploadDir = "uploads/";
             File dir = new File(uploadDir);
             if (!dir.exists()) {
                 dir.mkdirs();
             }

             String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
             Path filePath = Paths.get(uploadDir, filename);
             Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
             newRapport.setUrl(filename);
             rapportRepository.save(newRapport);
             return newRapport;
         }catch (Exception e){
             e.printStackTrace();
             return null;
         }
    }

    public void deleteRapport (Long id){
        Rapport rapport = rapportRepository.findById(id).orElse(null);

        String uploadDir = "uploads/";
        Path filePath = Paths.get(uploadDir, rapport.getUrl());
        try {
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        rapportRepository.delete(rapport);
    }

}
