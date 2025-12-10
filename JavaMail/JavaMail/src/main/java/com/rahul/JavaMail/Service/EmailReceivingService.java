package com.rahul.JavaMail.Service;

import com.rahul.JavaMail.Configurations.MailProperties;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.search.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

@Service
@AllArgsConstructor
public class EmailReceivingService {

    private final MailProperties mailProperties;

    public Store getPrerequisites(){
        Properties properties = new Properties();
        properties.put("mail.store.protocol","imaps");

        try {

            Store store = Session.getInstance(properties).getStore("imaps");
            store.connect(
                    mailProperties.getHost(),
                    mailProperties.getUsername(),
                    mailProperties.getPassword());
            return store;

        } catch (NoSuchProviderException e) {
            throw new RuntimeException(e);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public void getUnreadMessages(){
        Store store = getPrerequisites();

        try(Folder folder = store.getFolder("INBOX")){

            folder.open(Folder.READ_ONLY);
            Message[] unreadMessages = folder.search(new FlagTerm(new Flags(Flags.Flag.SEEN),false));
            System.out.println("Found " + unreadMessages.length + " unread messages!!");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public void getMailsFromSpecificSender(){
        Store store = getPrerequisites();
        try(Folder folder = store.getFolder("INBOX")){

            folder.open(Folder.READ_ONLY);
            SearchTerm fromFilter = new FromTerm(new InternetAddress("indramalkangari1515@gmail.com"));
            Message[] messages = folder.search(fromFilter);
            System.out.println("Found "+messages.length+" mails from indramalkangari1515@gmail.com");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public void getMailsFromSubjectFilter(){
        Store store=getPrerequisites();
        try(Folder folder=store.getFolder("INBOX")){

            folder.open(Folder.READ_ONLY);
            SearchTerm subjectFilter=new SubjectTerm("Important");
            Message messages[]=folder.search(subjectFilter);
            System.out.println("Found "+messages.length+" matched mails that has the word \"Important\" in the subject");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public void getMailsFromDateFilter(){
        Store store = getPrerequisites();
        try(Folder folder = store.getFolder("INBOX")){

            folder.open(Folder.READ_ONLY);
            Calendar calendar = Calendar.getInstance();
            calendar.set(2025,Calendar.SEPTEMBER,1);
            Date targetDate = calendar.getTime();

            SearchTerm dateFilter = new SentDateTerm(ComparisonTerm.GT,targetDate);
            Message[] message = folder.search(dateFilter);
            System.out.println(message.length+" message found from 1 september 2025");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public void getUnreadMailsFromSpecificSender(){
        Store store = getPrerequisites();
        try(Folder folder = store.getFolder("INBOX")){
            folder.open(Folder.READ_ONLY);
            SearchTerm fromFilter = new FromTerm(new InternetAddress("indramalkangari1515@gmail.com"));
            SearchTerm unreadFilter = new FlagTerm(new Flags(Flags.Flag.SEEN),false);
            SearchTerm combinedFilter = new AndTerm(fromFilter,unreadFilter);
            Message[] messages = folder.search(combinedFilter);
            System.out.println("Found "+messages.length+" unread mails which are related to indramalkangari1515@gmai.com ");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public void findEmailsReceivedRecently(){
        Store store = getPrerequisites();
        try(Folder folder = store.getFolder("INBOX")){
            folder.open(Folder.READ_ONLY);

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.DAY_OF_MONTH,-7);
            Date targetDate=calendar.getTime();

            SearchTerm receivedDateFilter = new ReceivedDateTerm(ComparisonTerm.GE,targetDate);
            Message[] messages = folder.search(receivedDateFilter);
            System.out.println("Found " + messages.length + " emails received in the last 7 days.");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

}
