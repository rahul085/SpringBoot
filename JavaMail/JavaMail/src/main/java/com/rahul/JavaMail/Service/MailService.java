package com.rahul.JavaMail.Service;

import com.rahul.JavaMail.Configurations.MailProperties;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.search.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;

import java.util.*;

@Service
public class MailService {
    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;
    private final MailProperties mailProperties;

    public MailService(JavaMailSender javaMailSender, TemplateEngine templateEngine,MailProperties properties) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
        this.mailProperties=properties;
    }

    @Value("${spring.mail.username}")
    private String from;
    private String host;

    @Async("taskExecutor")
    public String sendEmail(String to,String subject,String body){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setText(body);
        message.setTo(to);
        message.setSubject(subject);
        message.setFrom(from);
        javaMailSender.send(message);
        return "Message sent successfully!";
    }

    @Async("taskExecutor")
    public String sendEmailWithAttachments(String to,String subject, String htmlBody,String path) {
        try {

            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setText(htmlBody,true);
            helper.setTo(to);
            helper.setFrom(from);
            helper.setSubject(subject);
            ClassPathResource file = new ClassPathResource(path);
            helper.addAttachment(file.getFilename(),file);
            javaMailSender.send(mimeMessage);
            return "Mail sent with an attachment successfully ";

        } catch (MessagingException e){
            throw new RuntimeException(e);
        }
    }

    public void receiveEmail(){
        Properties properties=new Properties();
        properties.put("mail.store.protocol", "imaps");

        try(Store store = Session.getInstance(properties).getStore("imaps")){

            // 1.Connect to the mail server
            store.connect(
                    mailProperties.getHost(),
                    mailProperties.getUsername(),
                    mailProperties.getPassword());

            try(Folder inbox = store.getFolder("[Gmail]/Sent Mail")){

                // 2. Open the folder in read only mode\
                inbox.open(Folder.READ_ONLY);

                // Search for all unread  messages
                Message[] messages = inbox.search(new FlagTerm(new Flags(Flags.Flag.SEEN),true));
                System.out.println("Found "+ messages.length+" read messages");

                // 3. Process each message
                Arrays.stream(messages).limit(10).forEach(message-> {
                    try {
                        System.out.println(message.getSubject());
                    } catch (MessagingException e) {
                        throw new RuntimeException(e);
                    }
                });
            }

        } catch (NoSuchProviderException e) {
            throw new RuntimeException(e);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    // Demonstrate all flags
    public void demonstrateAllFlags(){
        Properties properties = new Properties();
        properties.put("mail.store.protocol","imaps");

        try(Store store = Session.getInstance(properties).getStore("imaps")){
            store.connect(mailProperties.getHost(),mailProperties.getUsername(),mailProperties.getPassword());

            try(Folder folder=store.getFolder("INBOX")){
                folder.open(Folder.READ_WRITE);
                System.out.println("\n Part 1: Searching ");

                // Search for  unread files
                FlagTerm unreadMessages = new FlagTerm(new Flags(Flags.Flag.SEEN),false);
                Message [] messages=folder.search(unreadMessages);
                System.out.println("There are total " + messages.length + " unread messages!");

                // returns all the folders of email server
                List<Folder> folders = List.of(store.getDefaultFolder().list("*"));
                System.out.println(folders);

                // Search for flagged(Starred) messages
                FlagTerm flagged = new FlagTerm(new Flags(Flags.Flag.FLAGGED),true);
                Message[] flaggedMessages=folder.search(flagged);
                System.out.println("There are total " + flaggedMessages.length + " flagged messages!");

                // Search for Answered messages
                FlagTerm answered=new FlagTerm(new Flags(Flags.Flag.ANSWERED),true);
                Message[] answeredMessages = folder.search(answered);
                System.out.println("There are total " + answeredMessages.length + " answered messages!");

                // Modifying flags on a message
                System.out.println("\n[Part 2: Modifying flags on a message]");
                if(messages.length>0) {

                    Message messageToModify = messages[0];
                    System.out.println("Printing the subject of first message" + messageToModify.getSubject());

                    System.out.println("Marking the message as read(SEEN=True)..");
                    messageToModify.setFlag(Flags.Flag.SEEN, true);

                    System.out.println("Starring message[FLAGGED=True]");
                    messageToModify.setFlag(Flags.Flag.FLAGGED, true);

                    System.out.println("Marking the message as answered(ANSWERED=True)");
                    messageToModify.setFlag(Flags.Flag.ANSWERED, true);

                    System.out.println("Flags successfully set on the message...");

                } else{
                    System.out.println("No unread messages found to modify");
                }
            }

        } catch (NoSuchProviderException e) {
            throw new RuntimeException(e);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public void demonstrateAllTerms(){
        Properties properties = new Properties();
        properties.put("mail.store.protocol","imaps");

        try(Store store = Session.getInstance(properties).getStore("imaps")){
            store.connect(mailProperties.getHost(),mailProperties.getUsername(),mailProperties.getPassword());

            try(Folder folder = store.getFolder("INBOX")){
                folder.open(Folder.READ_ONLY);
                // FromTerm is used to get mails from a particular mail
                Address fromAddress = new InternetAddress("indramalkangari1515@gmail.com");
                SearchTerm fromFilter = new FromTerm(fromAddress);
                Message[] messages = folder.search(fromFilter);
                System.out.println("Found " + messages.length + " exact matches for " + fromAddress);

                // SubjectTerm filters emails by searching for a specific word or phrase within the subject line.
                // The search is case-insensitive.

                SearchTerm subjectFilter = new SubjectTerm("Important");
                Message [] subjectFilterMessages = folder.search(subjectFilter);
                System.out.println("Found " + subjectFilterMessages.length + " exact matches from  Important pattern in subject");

                // SentDateTerm :- This term filters emails based on their sent date.
                // You must provide a comparison operator (like "equal to," "less than," etc.) and a Date object.
                Calendar calender= Calendar.getInstance();
                calender.set(2025,Calendar.OCTOBER,1);
                Date targetDate= calender.getTime();

                SearchTerm dateFilter=new SentDateTerm(ComparisonTerm.GT,targetDate);
                Message dateFilterMessages[]=folder.search(dateFilter);
                System.out.println("Found "+dateFilterMessages.length+" messages sent after october 1 2025");
            }

        } catch (NoSuchProviderException e) {
            throw new RuntimeException(e);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
