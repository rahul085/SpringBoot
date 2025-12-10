package com.rahul.JavaMail;

import com.rahul.JavaMail.Model.ProcessedEmail;
import com.rahul.JavaMail.Service.EmailProcessingService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EmailScheduler {
    private final EmailProcessingService service;

    public EmailScheduler(EmailProcessingService service) {
        this.service = service;
    }

    @Scheduled(fixedRate = 300000)
    public void checkEmailsPeriodically() {
        System.out.println("SCHEDULER: Running email check...");

        List<ProcessedEmail> processedEmails = service.processUnreadEmails();

        if (!processedEmails.isEmpty()) {
            System.out.println("SCHEDULER: Successfully processed " + processedEmails.size() + " emails.");

        } else {
            System.out.println("SCHEDULER: No new emails found.");
        }
    }
}
