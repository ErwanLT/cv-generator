package com.eletutour.cvgenerator.services;

import com.eletutour.cvgenerator.model.CV;
import com.eletutour.cvgenerator.model.Experiences;
import org.docx4j.model.structure.SectionWrapper;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.exceptions.InvalidFormatException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.Part;
import org.docx4j.openpackaging.parts.PartName;
import org.docx4j.openpackaging.parts.WordprocessingML.HeaderPart;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.relationships.Relationship;
import org.docx4j.wml.*;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

import static com.eletutour.cvgenerator.utils.FileUtils.makeParagraph;

@Service
public class FileServices {

    private static ObjectFactory factory = new ObjectFactory();

    public File genererCVDocx(String filenameToReturn, CV cv) {

        File exportFile = null;

        try {
            WordprocessingMLPackage pkg = WordprocessingMLPackage.createPackage();
            MainDocumentPart main_part = pkg.getMainDocumentPart();

            Relationship content_hdr_rel = createHeader(cv, pkg, main_part);

            //PUT THE DOCUMENT TOGETHER
            List<SectionWrapper> sections = pkg.getDocumentModel().getSections();

            // Get last section SectPr and create a new one if it doesn't exist
            SectPr sectPr = sections.get(sections.size() - 1).getSectPr();
            if (sectPr == null) {
                sectPr = factory.createSectPr();
                pkg.getMainDocumentPart().addObject(sectPr);
                sections.get(sections.size() - 1).setSectPr(sectPr);
            }

            // link content headers
            HeaderReference hdr_ref; // this variable is reused
            hdr_ref = factory.createHeaderReference();
            hdr_ref.setId(content_hdr_rel.getId());
            hdr_ref.setType(HdrFtrRef.DEFAULT);
            sectPr.getEGHdrFtrReferences().add(hdr_ref);

            main_part.addStyledParagraphOfText("Title", "CV " + cv.getCoordonnees().getNomFamille() + " " + cv.getCoordonnees().getPrenom());

            main_part.addStyledParagraphOfText("Subtitle", "Experiences");
            addExperience(main_part, cv.getExperiences());

            exportFile = new File(filenameToReturn +".docx");
            pkg.save(exportFile);
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } catch (Docx4JException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return exportFile;
    }

    private void addExperience(MainDocumentPart main_part, List<Experiences> experiences) {
        for (Experiences exp : experiences) {
            main_part.addParagraphOfText(exp.toString());
        }
    }

    private Relationship createHeader(CV cv, WordprocessingMLPackage pkg, MainDocumentPart main_part) throws InvalidFormatException {
        HeaderPart content_hdr_part = new HeaderPart(
                new PartName("/word/content-header.xml"));
        pkg.getParts().put(content_hdr_part);

        Hdr content_hdr = factory.createHdr();

        // Bind the header JAXB elements as representing their header parts
        content_hdr_part.setJaxbElement(content_hdr);

        // Add the reference to both header parts to the Main Document Part
        Relationship content_hdr_rel = main_part
                .addTargetPart(content_hdr_part);

        content_hdr.getContent().add(
                makeParagraph(content_hdr_part, cv.getCoordonnees().toString()));
        return content_hdr_rel;
    }

}
