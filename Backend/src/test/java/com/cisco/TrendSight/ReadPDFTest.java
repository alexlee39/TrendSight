package com.cisco.TrendSight;

import com.cisco.TrendSight.service.PDFService;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.junit.jupiter.api.Test;

public class ReadPDFTest {
    @Test
    public void readTextFromPDF(){
        PDFService pdfService = new PDFService();
//        System.out.println("Working Directory: " + System.getProperty("user.dir"));
//        System.out.println(System.getProperty("user.dir") + "\\pdfs\\LLMs.pdf");
//        System.out.println("D:\\Projects\\TrendSight\\Backend\\pdfs\\LLMs.pdf");
        String filepath = "D:\\Projects\\TrendSight\\Backend\\pdfs\\LLMs.pdf";
        System.out.println(pdfService.extractTextFromPDF(filepath));
    }
}
