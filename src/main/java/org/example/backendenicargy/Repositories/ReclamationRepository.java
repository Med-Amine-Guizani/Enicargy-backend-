package org.example.backendenicargy.Repositories;

import org.example.backendenicargy.Models.Reclamation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReclamationRepository extends JpaRepository<Reclamation, Long> {
    Long countByUser_idAndStatus(Long userid , String status);
    Long countByStatus(String status);
    List<Reclamation> findByUserId(Long userId);
    long countByUserRole(String role);
    public interface MonthlyReclamationStats {
        Integer getMois();
        Long getCount();
        Long getTerminees();
        Long getTotal();
    }

    @Query(value =
            "SELECT MONTH(STR_TO_DATE(r.date, '%Y-%m-%d %H:%i:%s')) AS mois,  " +
                    "       COUNT(*)                                     AS total, " +
                    "       SUM(CASE WHEN r.status = 'Terminer' THEN 1 ELSE 0 END) AS terminees " +
                    "FROM reclamation r " +
                    "GROUP BY MONTH(STR_TO_DATE(r.date, '%Y-%m-%d %H:%i:%s')) " +
                    "ORDER BY mois",
            nativeQuery = true)
    List<MonthlyReclamationStats> countMonthlyStats();
    public interface MonthlyReclamationStatus {
        Integer getMois();
        Long getEnAttente();
        Long getTerminees();
        Long getEnCours();
    }
    @Query(value =
            "SELECT MONTH(STR_TO_DATE(r.date, '%Y-%m-%d %H:%i:%s')) AS mois, " +
                    "       SUM(CASE WHEN r.status = 'En_Attente' THEN 1 ELSE 0 END) AS enAttente, " +
                    "       SUM(CASE WHEN r.status = 'Terminer' THEN 1 ELSE 0 END) AS terminees, " +
                    "       SUM(CASE WHEN r.status = 'En_Cours' THEN 1 ELSE 0 END) AS enCours " +
                    "FROM reclamation r " +
                    "GROUP BY MONTH(STR_TO_DATE(r.date, '%Y-%m-%d %H:%i:%s')) " +
                    "ORDER BY mois",
            nativeQuery = true)
    List<MonthlyReclamationStatus> countMonthlyStatus();



}
