package com.eletutour.cvgenerator.utils;

import org.docx4j.openpackaging.parts.Part;
import org.docx4j.wml.*;

public class FileUtils {

    private static ObjectFactory factory = new ObjectFactory();

    public static P makePageBr() throws Exception {
        P p = factory.createP();
        R r = factory.createR();
        Br br = factory.createBr();
        br.setType(STBrType.PAGE);
        r.getContent().add(br);
        p.getContent().add(r);
        return p;
    }

    public static P makeParagraph(Part part, String text) {
        P p = factory.createP();
        R r = factory.createR();
        p.getContent().add(r);
        org.docx4j.wml.Text t = factory.createText();
        r.getContent().add(t);
        t.setValue(text);
        return p;
    }
}
