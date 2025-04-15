package org.example.backendenicargy.repositories;

import org.example.backendenicargy.Models.Reclamation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReclamationRepository extends JpaRepository<Reclamation, Long> {
    Long countByUseridAndStatus(Long userid , String status);
}
