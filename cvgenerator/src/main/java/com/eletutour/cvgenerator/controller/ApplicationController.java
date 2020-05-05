package com.eletutour.cvgenerator.controller;

import com.eletutour.cvgenerator.form.CoordonneesForm;
import com.eletutour.cvgenerator.form.ExperienceForm;
import com.eletutour.cvgenerator.model.Adresse;
import com.eletutour.cvgenerator.model.CV;
import com.eletutour.cvgenerator.model.Coordonnees;
import com.eletutour.cvgenerator.model.Experiences;
import com.eletutour.cvgenerator.services.FileServices;
import com.eletutour.cvgenerator.services.PdfCreator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Controller
@Slf4j
public class ApplicationController {

    private static final String version = "1.0";

    private final FileServices fileServices;

    private final PdfCreator pdfCreator;

    private CV cv;

    private int experienceId = 0;

    @Autowired
    public ApplicationController(FileServices fileServices, PdfCreator pdfCreator){
        cv = new CV();
        this.fileServices = fileServices;
        this.pdfCreator = pdfCreator;
    }

    @GetMapping("/")
    public String index(Model model) {

        CoordonneesForm coordonneesForm = new CoordonneesForm();
        model.addAttribute("coordonneesForm",coordonneesForm);

        return "home";
    }

    @PostMapping("/experiences")
    public String next(@ModelAttribute("coordonneesForm")CoordonneesForm coordonneesForm, Model model){

        Coordonnees coordonnees = new Coordonnees()
                .withNomFamille(coordonneesForm.getNomFamille())
                .withPrenom(coordonneesForm.getPrenom())
                .withAdresse(new Adresse()
                    .withRue(coordonneesForm.getAdresse().getRue())
                    .withCodePostal(coordonneesForm.getAdresse().getCodePostal())
                    .withVille(coordonneesForm.getAdresse().getVille()))
                .withEmail(coordonneesForm.getEmail())
                .withNumeroTelephone(coordonneesForm.getNumeroTelephone());

        cv.setCoordonnees(coordonnees);

        model.addAttribute("cv", cv);

        return "experiences";
    }


    @GetMapping("/addExperience")
    public String addExperiences(Model model){

        ExperienceForm experienceForm = new ExperienceForm();
        model.addAttribute("experienceForm", experienceForm);

        return "addExperience";
    }

    @PostMapping("/addExp")
    public String addExp(@ModelAttribute("experienceForm")ExperienceForm experienceForm, Model model){

        Experiences exp = new Experiences()
                .withId(experienceId)
                .withNomEntreprise(experienceForm.getNomEntreprise())
                .withPoste(experienceForm.getPoste())
                .withDateDebut(experienceForm.getDateDebut())
                .withDateFin(experienceForm.getDateFin())
                .withDescription(experienceForm.getDescription());

        if(!StringUtils.isEmpty(experienceForm.getCompetences())){
            List<String> competences = Arrays.asList(experienceForm.getCompetences().split(","));
            exp.withCompetences(competences);
        }

        experienceId++;

        cv.getExperiences().add(exp);

        model.addAttribute("cv", cv);

        return "experiences";
    }


    @GetMapping("/genererCV")
    @ResponseBody
    public ResponseEntity<InputStreamResource> serveFile() {

        InputStreamResource isr = null;
        HttpHeaders respHeader = new HttpHeaders();

        try {

            String filenameToReturn = "CV_"+cv.getCoordonnees().getPrenom();

            //File fileToReturn = fileServices.genererCVDocx(filenameToReturn, cv);

            File fileToReturn = pdfCreator.genererCVPDF(filenameToReturn, cv);


            respHeader.setContentDispositionFormData("attachment", fileToReturn.getName());
            isr = new InputStreamResource(new FileInputStream(fileToReturn));

        } catch (FileNotFoundException e){
            log.error("erreur");
        }

        return new ResponseEntity<>(isr, respHeader, HttpStatus.OK);
    }

}
