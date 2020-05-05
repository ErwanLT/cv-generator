package com.eletutour.cvgenerator.services;

import com.eletutour.cvgenerator.model.CV;
import com.eletutour.cvgenerator.model.Coordonnees;
import com.eletutour.cvgenerator.model.Experiences;
import com.itextpdf.html2pdf.HtmlConverter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class PdfCreator {




    public File genererCVPDF(String filenameToReturn, CV cv) {

        File file = new File(filenameToReturn+".pdf");

        StringBuilder html = new StringBuilder();
        getCoordonnees(html, cv.getCoordonnees());
        getTitle(html, "CV " + cv.getCoordonnees().getNomFamille() + " " + cv.getCoordonnees().getPrenom());
        getExperiences(html, cv.getExperiences());


        try {
            HtmlConverter.convertToPdf(html.toString(), new FileOutputStream(file));

            file.createNewFile();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return  file;
    }

    private void getCoordonnees(StringBuilder html, Coordonnees coordonnees) {
        html.append("<p>").append(coordonnees.getNomFamille() + " " + coordonnees.getPrenom()).append("<br>")
                .append(coordonnees.getAdresse().getRue()).append("<br>")
                .append(coordonnees.getAdresse().getCodePostal() + " " + coordonnees.getAdresse().getRue()).append("<br>")
                .append(coordonnees.getNumeroTelephone()).append("<br>")
                .append(coordonnees.getEmail()).append("</p>");

    }

    private void getTitle(StringBuilder html, String title){
        html.append("<div style=\"background-color : #0398fc; text-align: center; font-weight: bold;\">")
                .append("<h1>")
                    .append(title)
                .append("</h1>")
        .append("</div>");
    }

    private void getExperiences(StringBuilder html, List<Experiences> experiences) {
        //Titre de section
        html.append("<div style=\"background-color : #66c2ff; text-align: left; font-weight: bold;\">")
            .append("<h2>")
                .append("Experiences")
            .append("</h2>")
        .append("</div>");

        for (Experiences exp : experiences ) {
            html.append("<div style=\"background-color : #b0dfff; text-align: left; font-weight: bold;\">")
                .append(exp.getNomEntreprise())
            .append("</div>")
            .append("<div>")
                .append(exp.getPoste()).append("<br>")
                .append(exp.getDateDebut()).append( " - ").append((exp.getDateFin()!=null && !StringUtils.isEmpty(exp.getDateFin()))?exp.getDateFin():"Présent").append("<br>")
                .append(exp.getDescription()!=null?exp.getDescription():"").append("<br>");
            if(!exp.getCompetences().isEmpty()){
                html.append("<ul>");
                for (String competence:exp.getCompetences()) {
                    html.append("<li>" + competence + "</li>");
                }
                html.append("</ul>");
            }
            html.append("</div>");
        }

    }

}
