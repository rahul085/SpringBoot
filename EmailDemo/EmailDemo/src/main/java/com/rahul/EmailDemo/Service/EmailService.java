package com.rahul.EmailDemo.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;

@Service
@Slf4j
public class EmailService {
    @Value("${spring.mail.username}")
    private String from;

    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    public EmailService(JavaMailSender javaMailSender, TemplateEngine templateEngine) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
    }

    @Async
    public String sendEmail(String to, String subject, String body){
        SimpleMailMessage simpleMailMessage=new SimpleMailMessage();
        simpleMailMessage.setFrom(from);
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(body);
        javaMailSender.send(simpleMailMessage);
        return "Message sent successfully!";
    }

    @Async
    public void sendMessageWithAttachment(String to,String subject,String htmlBody,String pathToAttachment) throws MessagingException {
        MimeMessage message= javaMailSender.createMimeMessage();
        MimeMessageHelper helper=new MimeMessageHelper(message,true);

        helper.setFrom(from);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlBody,true);

        ClassPathResource file =new ClassPathResource(pathToAttachment);
        helper.addAttachment(file.getFilename(),file);

        javaMailSender.send(message);
    }

    @Async
    @Retryable(
            value={MailException.class},
            maxAttempts = 3,
            backoff = @Backoff(delay = 2000)
    )
    public void sendMessageUsingOriginalPath(String to,String subject,String htmlBody,String pathToAttachment) throws MessagingException{
        Context context=new Context();
        context.setVariable("name","Indra");

        String process=templateEngine.process("welcome-email.html",context);

        MimeMessage message=javaMailSender.createMimeMessage();
        MimeMessageHelper helper=new MimeMessageHelper(message,true);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(process,true);

        FileSystemResource file=new FileSystemResource(new File("C:\\Users\\Sreenivas Bandaru\\Desktop\\hello.txt"));
        helper.addAttachment(file.getFilename(),file);

        System.out.println("Attempting to send mail");
        javaMailSender.send(message);
        System.out.println("Mail sent successfully!");
    }

    @Recover
    public void recover(MailException e, String to, String subject, String text) {
        log.error("Failed to send email after multiple retries to: " + to, e);
        // You could save the failed email to a database to be sent later
    }
}
