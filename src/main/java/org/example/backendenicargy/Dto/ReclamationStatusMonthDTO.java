package org.example.backendenicargy.Dto;

public class ReclamationStatusMonthDTO {
    private String mois;
    private Long enAttente;
    private Long terminees;
    private Long enCours;

    public ReclamationStatusMonthDTO(String mois, Long enAttente, Long terminees, Long enCours) {
        this.mois = mois;
        this.enAttente = enAttente;
        this.terminees = terminees;
        this.enCours=enCours;
    }

    public String getMois() { return mois; }

    public Long getEnAttente() {return enAttente;}

    public Long getTerminees() { return terminees; }

    public Long getEnCours() {return enCours;}
}
