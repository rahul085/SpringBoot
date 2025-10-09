package com.rahul.PdfDemo.Service;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class DocumentService {

    public String readFromPdf(MultipartFile file){
        StringBuilder sb=new StringBuilder();
        try(PDDocument document=PDDocument.load(file.getInputStream())){
            PDFTextStripper stripper=new PDFTextStripper();
            sb.append(stripper.getText(document));
            return sb.toString();


        } catch(IOException e){
            throw new RuntimeException();
        }

    }

    public String readFromScannedPdf(MultipartFile file){
        StringBuilder stringBuilder=new StringBuilder();
        try(PDDocument document=PDDocument.load(file.getInputStream())) {
            PDFRenderer renderer=new PDFRenderer(document);
            ITesseract tesseract=new Tesseract();
            tesseract.setDatapath("C:\\Program Files\\Tesseract-OCR\\tessdata");
            tesseract.setLanguage("eng");
            for(int i=0;i<document.getNumberOfPages();i++){
                BufferedImage image=renderer.renderImageWithDPI(i,300);
                String text=tesseract.doOCR(image);
                stringBuilder.append(text);
            }

            return stringBuilder.toString();



        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (TesseractException e) {
            throw new RuntimeException(e);
        }
    }


    public List<List<String>> readFromExcel(MultipartFile file){
        List<List<String>> data=new ArrayList<>();
        try(Workbook workbook= WorkbookFactory.create(file.getInputStream())) {
            Sheet sheet=workbook.getSheetAt(0);
            for(Row row:sheet){
                List<String> rowData=new ArrayList<>();
                for(Cell cell:row){
                    String cellValue=switch (cell.getCellType()){
                        case STRING -> cell.getStringCellValue();
                        case NUMERIC -> {
                            if(DateUtil.isCellDateFormatted(cell)){
                                SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
                               yield  dateFormat.format(cell.getDateCellValue());

                            }
                            else{
                                yield String.valueOf(cell.getNumericCellValue());
                            }
                        }
                        case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
                        case FORMULA -> cell.getCellFormula();
                        case BLANK -> "";
                        default -> "";
                    };
                    rowData.add(cellValue);
                }
                data.add(rowData);
            }
            return data;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
