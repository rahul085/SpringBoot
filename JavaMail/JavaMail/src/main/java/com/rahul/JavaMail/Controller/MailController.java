package com.rahul.JavaMail.Controller;

import com.rahul.JavaMail.Service.EmailReceivingService;
import com.rahul.JavaMail.Service.MailService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")

public class MailController {

    private final MailService service;
    private final EmailReceivingService emailReceivingService;

    public MailController(MailService service, EmailReceivingService emailReceivingService) {
        this.service = service;
        this.emailReceivingService = emailReceivingService;
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendMail(@RequestParam String to,@RequestParam String subject,@RequestParam String body){
        return new ResponseEntity<>(service.sendEmail(to,subject,body), HttpStatusCode.valueOf(200));
    }

    @PostMapping("/attach")
    public ResponseEntity<String> sendMailWithAttachment(@RequestParam String to,@RequestParam String subject,@RequestParam String htmlBody,String path){
        return new ResponseEntity<>(service.sendEmailWithAttachments(to,subject,htmlBody,path), HttpStatus.OK);
    }

}
