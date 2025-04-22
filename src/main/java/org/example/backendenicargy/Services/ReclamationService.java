package org.example.backendenicargy.Services;


import org.example.backendenicargy.Models.User;
import org.example.backendenicargy.Repositories.ReclamationRepository;
import org.example.backendenicargy.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReclamationService {
    @Autowired
    private ReclamationRepository reclamationRepo;
    @Autowired
    private UserRepository userRepo;


    public Optional<User> userForRec(Long id){
        return userRepo.findById(id);
    }

}
