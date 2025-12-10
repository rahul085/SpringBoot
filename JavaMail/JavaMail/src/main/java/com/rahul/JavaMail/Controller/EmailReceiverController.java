package com.rahul.JavaMail.Controller;

import com.rahul.JavaMail.Service.EmailReceivingService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/receive")
public class EmailReceiverController {

    private EmailReceivingService emailReceivingService;

    @GetMapping("/unread-emails")
    public void receiveEmails(){
        emailReceivingService.getUnreadMessages();
    }

    @GetMapping("/specific-emails")
    public void specificEmails(){
        emailReceivingService.getMailsFromSpecificSender();
    }

    @GetMapping("/pattern-emails")
    public void patternMails(){
        emailReceivingService.getMailsFromSubjectFilter();
    }

    @GetMapping("/emails-by-date")
    public void emailsByDate(){
        emailReceivingService.getMailsFromDateFilter();
    }

    @GetMapping("/emails-by-conditions")
    public void emailsBYAndFilter(){
        emailReceivingService.getUnreadMailsFromSpecificSender();
    }

    @GetMapping("/emails-by-received-date")
    public void emailsByReceivedDate(){
        emailReceivingService.findEmailsReceivedRecently();
    }

}

