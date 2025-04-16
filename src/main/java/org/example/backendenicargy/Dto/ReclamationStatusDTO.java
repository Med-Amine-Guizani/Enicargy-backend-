package org.example.backendenicargy.Dto;

public class ReclamationStatusDTO {
    private long en_attente;
    private long en_cours;
    private long terminer;

    // Constructors
    public ReclamationStatusDTO(long en_attente, long en_cours, long terminer) {
        this.en_attente = en_attente;
        this.en_cours = en_cours;
        this.terminer = terminer;
    }

    // Getters & Setters
    public long getEn_attente() { return en_attente; }
    public void setEn_attente(long en_attente) { this.en_attente = en_attente; }

    public long getEn_cours() { return en_cours; }
    public void setEn_cours(long en_cours) { this.en_cours = en_cours; }

    public long getTerminer() { return terminer; }
    public void setTerminer(long terminer) { this.terminer = terminer; }
}
