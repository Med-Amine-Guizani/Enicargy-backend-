package org.example.backendenicargy.Repositories;

import org.example.backendenicargy.Models.Reclamation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReclamationRepository extends JpaRepository<Reclamation, Long> {
    Long countByUser_idAndStatus(Long userid , String status);
}
