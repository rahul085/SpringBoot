package com.rahul.EmailDemo;

import com.rahul.EmailDemo.Service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@EnableRetry
public class EmailDemoApplication implements CommandLineRunner {
    @Autowired
    private EmailService emailService;

    public static void main(String[] args) {
        SpringApplication.run(EmailDemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
//        String htmlBody="<h> Hi Indra!</h>";
//        String filePath="Attachment/hello.txt";
//        emailService.sendMessageWithAttachment("indramalkangari1515@gmail.com","Important",htmlBody,filePath);
        String htmlBody = "<h> Hi Indra!. This is to inform that we are no longer friends</h>";
        String filePath = "C:\\Users\\Sreenivas Bandaru\\Desktop\\hello.txt";

        emailService.sendMessageUsingOriginalPath("indramalkangari1515@gmail.com", "Relationship Update", htmlBody, filePath);
    }
}
