package com.eletutour.cvgenerator.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CV {

    private Coordonnees coordonnees;

    private List<Formation> formations;

    private List<Experiences> experiences = new ArrayList<>();
}
