package org.example.backendenicargy.Dto;

public class ReclamationByMonthDTO {
    private String mois;
    private Long total;
    private Long terminees;

    public ReclamationByMonthDTO(String mois, Long total, Long terminees) {
        this.mois = mois;
        this.total = total;
        this.terminees = terminees;
    }

    public String getMois() { return mois; }
    public Long getTotal() { return total; }
    public Long getTerminees() { return terminees; }
}
