package com.eletutour.cvgenerator.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class Formation {

    private String nomEtablissement;
    private String diplome;
    private Date dateDebut;
    private Date dateFin;
}
