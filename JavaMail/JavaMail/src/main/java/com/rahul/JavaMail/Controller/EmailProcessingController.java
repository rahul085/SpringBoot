package com.rahul.JavaMail.Controller;

import com.rahul.JavaMail.Model.ProcessedEmail;
import com.rahul.JavaMail.Service.EmailProcessingService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class EmailProcessingController {
    private EmailProcessingService service;

    @PostMapping("/processed-emails")
    public ResponseEntity<List<ProcessedEmail>> getAllUnreadMails(){
         return new ResponseEntity<>(service.processUnreadEmails(), HttpStatus.OK);
    }

}
