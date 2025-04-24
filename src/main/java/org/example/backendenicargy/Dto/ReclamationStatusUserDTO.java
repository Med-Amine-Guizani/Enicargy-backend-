package org.example.backendenicargy.Dto;

public class ReclamationStatusUserDTO {
    private String date;
    private String status;
    private String titre;

    public ReclamationStatusUserDTO(String date, String statut, String titre) {
        this.date = date;
        this.status = statut;
        this.titre = titre;
    }

    public String getDate(){
        return this.date;
    }

    public String getStatus(){
        return  this.status;
    }

    public String getTitre(){
        return this.titre;
    }

}
