package com.percipient.view;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.Barcode39;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfWriter;
import com.percipient.bean.Student;
import java.awt.Color;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SimpleStudentPDFView extends AbstractPdfView {

    public Set<String> skills = new HashSet<String>();

    @Override
    protected void buildPdfDocument(Map<String, Object> model,
            Document doc, PdfWriter writer, HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        Student student = (Student) model.get("stud");
        doc.open();

        doc.setPageSize(PageSize.ID_1);
        
        PdfContentByte cb = writer.getDirectContent();

        addBand(cb, Color.RED);
        addSkillsTable(cb);
        addSkils(cb);

        //addImage(cb, img);
        addBarcode(cb, 1234);
        addVolunteerName(cb, student.getFirstName() + " " + student.getLastName());
        addRBCRegionTitle(cb, "GSK");
        addDepartment(cb, "IT Systems Analyst");
        addBigRectangle(cb);
        addBadgeTitle(cb, "GlaxoSmithKline");
    }

    /**
     * Creates a rectangular band to represent the access a volunteer has to the
     * sites. Red means he is allowed to be in high-risk areas.
     *
     * @param content to added to the pdf
     * @param color of the volunteer's badge
     */
    private void addBand(PdfContentByte content, Color color) {
        content.roundRectangle(80, 700, 250, 7, 2.5f);
        content.setColorStroke(Color.RED);
        content.fill();
    }

    /**
     * Creates the perimeter of the Rectangular Badge.
     *
     * @param content the Rectangle
     */
    private void addBigRectangle(PdfContentByte content) {
        content.rectangle(80, 700, 250, -168);
        content.closePathStroke();
    }

    /**
     * Adds the volunteer's image to the badge.
     *
     * @param content add content
     * @param img the image
     */
    private void addImage(PdfContentByte content, Image img) {
        try {
            content.addImage(img, 75, 0, 0, 90, 247, 577);
        } catch (DocumentException ex) {
            Logger.getLogger(SimpleStudentPDFView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Adds a title to the badge.
     *
     * @param content to be added
     * @param title the title
     */
    private void addBadgeTitle(PdfContentByte content, String title) {
        try {
            content.beginText();
            content.moveText(141, 685);
            BaseFont bf = BaseFont.createFont();
            content.setFontAndSize(bf, 12);
            content.setColorFill(Color.BLACK);
            content.setTextRenderingMode(PdfContentByte.TEXT_RENDER_MODE_FILL_STROKE_CLIP);
            content.showText(title);
            content.endText();
        } catch (DocumentException ex) {
            Logger.getLogger(SimpleStudentPDFView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SimpleStudentPDFView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Creates a geometric table for a skills matrix.
     *
     * @param content to be added
     */
    private void addSkillsTable(PdfContentByte content) {
        // Horizontal lines of table
        int y = 0;
        for (int i = 0; i <= 8; i++) {
            content.moveTo(90, 635 - y);
            content.lineTo(180, 635 - y);
            content.closePathStroke();

            y += 12;
        }
        // left side
        content.moveTo(90, 635);
        content.lineTo(90, 539);
        content.closePathStroke();

        // right side
        content.moveTo(180, 635);
        content.lineTo(180, 539);
        content.closePathStroke();
    }

    /**
     * Adds the volunteer's skills to the Badge. Uses the y-axis to position
     * each individual skill.
     *
     * @param content to be added
     */
    private void addSkils(PdfContentByte content) {
        int y = 0;
        for (String skill : skills) {
            try {
                content.beginText();
                content.moveText(91, 625 - y);
                BaseFont bf = BaseFont.createFont();
                content.setFontAndSize(bf, 8);
                content.setColorFill(Color.BLACK);
                content.showText(skill);
                content.endText();
                y += 12;
            } catch (DocumentException ex) {
                Logger.getLogger(SimpleStudentPDFView.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(SimpleStudentPDFView.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Adds the Volunteer's name to the Badge.
     *
     * @param content to be added
     * @param name concatenated String of forename and surname
     */
    private void addVolunteerName(PdfContentByte content, String name) {
        content.beginText();
        content.moveText(90, 670);
        BaseFont bf = null;
        try {
            bf = BaseFont.createFont();
        } catch (DocumentException ex) {
            Logger.getLogger(SimpleStudentPDFView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SimpleStudentPDFView.class.getName()).log(Level.SEVERE, null, ex);
        }
        content.setFontAndSize(bf, 11);
        content.setColorFill(Color.BLACK);
        content.showText(name);
        content.endText();
    }

    /**
     * Adds the RBC Region's title (e.g. London and the Home Counties) to the
     * Badge.
     *
     * @param content to be added
     * @param rbcRegion RBC region
     */
    private void addRBCRegionTitle(PdfContentByte content, String rbcRegion) {
        content.beginText();
        content.moveText(90, 641);
        BaseFont bf = null;
        try {
            bf = BaseFont.createFont();
        } catch (DocumentException ex) {
            Logger.getLogger(SimpleStudentPDFView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SimpleStudentPDFView.class.getName()).log(Level.SEVERE, null, ex);
        }

        content.setFontAndSize(bf, 9);
        content.setColorFill(Color.DARK_GRAY);
        content.showText("-" + rbcRegion);
        content.endText();
    }

    /**
     * Adds a Barcode39 of the id number of a Volunteer to the Badge.
     *
     * @param content to be added
     * @param id volunteer id
     */
    private void addBarcode(PdfContentByte content, Integer id) {
        Barcode39 barcode = new Barcode39();
        barcode.setCode(id.toString());

        Image barcodeImage = barcode.createImageWithBarcode(content, null, null);
        try {
            content.addImage(barcodeImage, 100, 0, 0, 35, 220, 536);
        } catch (DocumentException ex) {
            Logger.getLogger(SimpleStudentPDFView.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Adds a department to the Badge. The badge only shows the first
     * departmental assignment of a Volunteer, not all their departmental
     * assignments.
     *
     * @param content to be added
     * @param department of the volunteer
     */
    private void addDepartment(PdfContentByte content, String department) {
        content.beginText();
        content.moveText(90, 653);
        BaseFont bf = null;
        try {
            bf = BaseFont.createFont();
        } catch (DocumentException ex) {
            Logger.getLogger(SimpleStudentPDFView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SimpleStudentPDFView.class.getName()).log(Level.SEVERE, null, ex);
        }

        content.setFontAndSize(bf, 10.5f);
        content.setColorFill(Color.BLACK);
        content.showText(department);
        content.endText();
    }
}
