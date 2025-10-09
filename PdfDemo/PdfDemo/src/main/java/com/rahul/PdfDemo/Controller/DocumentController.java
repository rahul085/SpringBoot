package com.rahul.PdfDemo.Controller;

import com.rahul.PdfDemo.Service.DocumentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class DocumentController {

    private final DocumentService service;
   @PostMapping("/textPdf")
    public ResponseEntity<String> readFromTextPdf(@RequestParam("file")MultipartFile file){
       return new ResponseEntity<>(service.readFromPdf(file), HttpStatus.OK);
   }

    @PostMapping("/scannedPdf")
    public ResponseEntity<String> readFromScannedPdf(@RequestParam("file")MultipartFile file){
        return new ResponseEntity<>(service.readFromScannedPdf(file), HttpStatus.OK);
    }

    @PostMapping("/excel")
    public ResponseEntity<List<List<String>>> readFromExcel(@RequestParam("file")MultipartFile file){
        return new ResponseEntity<>(service.readFromExcel(file), HttpStatus.OK);
    }
}
