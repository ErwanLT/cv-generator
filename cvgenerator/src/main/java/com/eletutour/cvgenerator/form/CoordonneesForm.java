package com.eletutour.cvgenerator.form;

import com.eletutour.cvgenerator.model.Adresse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CoordonneesForm {

    private String nomFamille;
    private String prenom;
    private AdresseForm adresse;
    private String numeroTelephone;
    private String email;

}
