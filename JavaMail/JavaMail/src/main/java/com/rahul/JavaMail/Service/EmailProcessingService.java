package com.rahul.JavaMail.Service;

import com.rahul.JavaMail.Configurations.MailProperties;
import com.rahul.JavaMail.Model.ProcessedEmail;
import jakarta.mail.*;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.search.*;
import lombok.AllArgsConstructor;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

@Service
@AllArgsConstructor
public class EmailProcessingService {

    private final MailProperties mailProperties;

    public List<ProcessedEmail> processUnreadEmails(){

            List<ProcessedEmail> processedEmails=new ArrayList<>();

            Properties properties=new Properties();
            properties.put("mail.store.protocol","imaps");

            try(Store store=Session.getInstance(properties).getStore("imaps")){
                store.connect(mailProperties.getHost(),mailProperties.getUsername(),mailProperties.getPassword());
                 try(Folder inbox=store.getFolder("INBOX")){
                     inbox.open(Folder.READ_WRITE);
                     SearchTerm unreadFilter=new FlagTerm(new Flags(Flags.Flag.SEEN),false);
                     SearchTerm fromFilter=new FromStringTerm("captainrogers577@gmail.com");
                     SearchTerm combinationFilter=new AndTerm(unreadFilter,fromFilter);

                     Message[] messages=inbox.search(combinationFilter);
                     System.out.println("Found "+messages.length+" unread mails from captain rogers");

                     int messagesToRead=Math.min(messages.length, 5);

                     for(int i=messages.length-1;i>=messages.length-messagesToRead;i--){
                         Message message=messages[i];
                         ProcessedEmail email=processMessage(message);
                         processedEmails.add(email);
                         message.setFlag(Flags.Flag.SEEN,true);

                     }
                 }
                    return processedEmails;
            } catch (NoSuchProviderException e) {
                throw new RuntimeException(e);
            } catch (MessagingException | IOException e) {
                throw new RuntimeException(e);
            }

    }


    private ProcessedEmail processMessage(Message message) throws MessagingException, IOException {
        String from=message.getFrom()[0].toString();
        String subject=message.getSubject();
        Date sentDate=message.getSentDate();

        StringBuilder body=new StringBuilder();
        List<String> attachmentFileNames=new ArrayList<>();

        Object content=message.getContent();

        if(message.isMimeType("text/plain")){
            body.append(content.toString());
        } else if(message.isMimeType("text/html")){
            body.append(Jsoup.parse(content.toString()).text());
        } else if(message.isMimeType("multipart/*")){
            parseMultipart((Multipart) content,body,attachmentFileNames);
        }

        return new ProcessedEmail(from,subject,body.toString(),sentDate,attachmentFileNames);


    }


    private void parseMultipart(Multipart multipart, StringBuilder body, List<String> attachmentFileNames)
            throws MessagingException, IOException {

        boolean foundPlainText = false;
        String htmlBody = null;

        for (int i = 0; i < multipart.getCount(); i++) {
            BodyPart bodyPart = multipart.getBodyPart(i);

            if (Part.ATTACHMENT.equalsIgnoreCase(bodyPart.getDisposition()) && bodyPart.getFileName() != null) {
                saveAttachment(bodyPart, attachmentFileNames);
            }
            else if (bodyPart.isMimeType("text/plain")) {
                // --- We found plain text! Use it. ---
                body.append(bodyPart.getContent().toString());
                foundPlainText = true;
            }
            else if (bodyPart.isMimeType("text/html")) {
                // --- We found HTML, save it just in case ---
                htmlBody = bodyPart.getContent().toString();
            }
            else if (bodyPart.isMimeType("multipart/*")) {
                // --- This is a nested part, recurse into it ---
                parseMultipart((Multipart) bodyPart.getContent(), body, attachmentFileNames);
            }
        }

        // --- FALLBACK LOGIC ---
        // After the loop, if we *never* found plain text but *did* find HTML,
        // use the HTML version (and parse it with Jsoup).
        if (!foundPlainText && htmlBody != null) {
            body.append(Jsoup.parse(htmlBody).text());
        }
    }

    private void saveAttachment(BodyPart bodyPart,List<String> attachmentFileNames) throws MessagingException, IOException {
        String fileName=bodyPart.getFileName();
        File dir=new File(mailProperties.getAttachmentStoragePath());
        if(!dir.exists()){
            dir.mkdirs();
        }
        File fileToSave=new File(dir,fileName);

        if(bodyPart instanceof MimeBodyPart){
            MimeBodyPart mimeBodyPart=(MimeBodyPart) bodyPart;
            mimeBodyPart.saveFile(fileToSave);
            attachmentFileNames.add(fileName);
        }

    }


}