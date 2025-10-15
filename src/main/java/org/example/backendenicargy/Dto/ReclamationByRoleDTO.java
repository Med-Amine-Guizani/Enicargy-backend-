package org.example.backendenicargy.Dto;




public class ReclamationByRoleDTO {
    private long etudiant;
    private long enseigneant;
    private long personnel;

    public ReclamationByRoleDTO(long etudiant, long enseigneant, long personnel) {
        this.etudiant = etudiant;
        this.enseigneant = enseigneant;
        this.personnel = personnel;
    }

    // Getters et setters
    public long getEtudiant() { return etudiant; }
    public void setEtudiant(long etudiant) { this.etudiant = etudiant; }

    public long getEnseigneant() { return enseigneant; }
    public void setEnseigneant(long enseigneant) { this.enseigneant = enseigneant; }

    public long getPersonnel() { return personnel; }
    public void setPersonnel(long personnel) { this.personnel = personnel; }
}

