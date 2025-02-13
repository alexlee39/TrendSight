package com.cisco.TrendSight.service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class PDFService {

    public String extractTextFromPDF(String filepath){
        File pdf = new File(filepath);
        try(PDDocument pdDocument = PDDocument.load(pdf)){
            PDFTextStripper stripper = new PDFTextStripper();
            String pdfText = stripper.getText(pdDocument);
            pdDocument.close();
            return pdfText;
        }catch(IOException e){
            return "Error Occurred: " + e.getMessage();
        }
    }

    public String extractTextFromPDF(File pdf){
        try(PDDocument pdDocument = PDDocument.load(pdf)){
            PDFTextStripper stripper = new PDFTextStripper();
            String pdfText = stripper.getText(pdDocument);
            pdDocument.close();
            return pdfText;
        }catch(IOException e){
            return "Error Occurred: " + e.getMessage(//            logger.error("Unexpected Interval Server Error Message: ", e);
            );
        }
    }
}
