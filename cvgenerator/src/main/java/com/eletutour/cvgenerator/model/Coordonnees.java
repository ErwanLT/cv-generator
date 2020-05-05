package com.eletutour.cvgenerator.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Coordonnees {

    private String nomFamille;
    private String prenom;
    private Adresse adresse;
    private String numeroTelephone;
    private String email;

    public Coordonnees withNomFamille(String nomFamille){
        setNomFamille(nomFamille);
        return this;
    }

    public Coordonnees withPrenom(String prenom){
        setPrenom(prenom);
        return this;
    }

    public Coordonnees withAdresse(Adresse adresse){
        setAdresse(adresse);
        return this;
    }

    public Coordonnees withNumeroTelephone(String numeroTelephone){
        setNumeroTelephone(numeroTelephone);
        return this;
    }

    public Coordonnees withEmail(String email){
        setEmail(email);
        return this;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append(nomFamille).append(" ").append(prenom).append("\n")
                .append(adresse).append("\n")
                .append(numeroTelephone).append("\n")
                .append(email)
                .toString();
    }
}
