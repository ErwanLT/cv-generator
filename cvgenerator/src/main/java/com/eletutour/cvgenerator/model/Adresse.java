package com.eletutour.cvgenerator.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Adresse {

    private String rue;
    private String codePostal;
    private String ville;

    public Adresse withRue(String rue){
        setRue(rue);
        return this;
    }

    public Adresse withCodePostal(String codePostal){
        setCodePostal(codePostal);
        return this;
    }

    public Adresse withVille(String ville){
        setVille(ville);
        return this;
    }

    @Override
    public String toString() {
        return rue + "\n" + codePostal +" "+ville;
    }
}
