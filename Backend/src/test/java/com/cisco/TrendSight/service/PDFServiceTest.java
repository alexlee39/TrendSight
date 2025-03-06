package com.cisco.TrendSight.service;

import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class PDFServiceTest {

    private PDFService pdfService;
    private Path tempFile;
    public final String dummyString = "Hello World";


    @BeforeEach
    public void setup() throws IOException {
        pdfService = new PDFService();
        //Create tempFile to read for Mocking Purposes
        tempFile = Files.createTempFile("uploaded", "pdf");
        // Use Apache PDFBox or another library to create a proper PDF
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);
            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                contentStream.newLineAtOffset(100, 700);
                contentStream.showText(dummyString);
                contentStream.endText();
            }
            document.save(tempFile.toFile());
        }
    }

    @AfterEach
    public void close() throws IOException {
        Files.deleteIfExists(tempFile);
    }

    /** How to handle new line characters from PDF's?
     *
     *  Solution: Strip trailing white spaces from extracted PDF Text
     */
    @Test
    void testExtractTextFromPDF() {
        File tempPDF = tempFile.toFile();
        String text = pdfService.extractTextFromPDF(tempFile.toFile()).stripTrailing();
        tempPDF.deleteOnExit();
        Assertions.assertNotNull(text);
        assertEquals(dummyString, text);
    }

}