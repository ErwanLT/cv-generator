package com.eletutour.cvgenerator.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Experiences {

    private int id;
    private String nomEntreprise;
    private String poste;
    private String dateDebut;
    private String dateFin;
    private String description;
    private List<String> competences;


    public Experiences withId(int id){
        setId(id);
        return this;
    }

    public Experiences withNomEntreprise(String nomEntreprise){
        setNomEntreprise(nomEntreprise);
        return this;
    }

    public Experiences withPoste(String poste){
        setPoste(poste);
        return this;
    }

    public Experiences withDateDebut(String dateDebut){
        setDateDebut(dateDebut);
        return this;
    }

    public Experiences withDateFin(String dateFin){
        setDateFin(dateFin);
        return this;
    }

    public Experiences withDescription(String description){
        setDescription(description);
        return this;
    }

    public Experiences withCompetences(List<String> competences){
        setCompetences(competences);
        return this;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("Societée : " + nomEntreprise).append("\n")
                .append("Poste occupé : " + poste).append("\n")
                .append("Du : " + dateDebut).append("    ").append("Au : " + dateFin).append("\n")
                .append("Description : " + description)
                .toString();
    }
}
