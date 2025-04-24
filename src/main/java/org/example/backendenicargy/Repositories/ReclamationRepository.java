package org.example.backendenicargy.Repositories;

import org.example.backendenicargy.Models.Reclamation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReclamationRepository extends JpaRepository<Reclamation, Long> {
    Long countByUser_idAndStatus(Long userid , String status);

    List<Reclamation> findByUserId(Long userId);

}
