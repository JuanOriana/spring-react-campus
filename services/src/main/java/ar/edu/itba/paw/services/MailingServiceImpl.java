package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.MailingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Collections;
import java.util.List;

@Service
public class MailingServiceImpl implements MailingService {

    private final Session session;

    private final String SERVER_MAIL = "mpvcampus@gmail.com";

    @Autowired
    public MailingServiceImpl(Session session) {
        this.session = session;
    }


    @Async
    @Override
    public void sendTextPlainBroadcastEmail(List<String> to, String subject, String content) {
        sendEmail(new MimeMessage(session), to, subject, content, "text/plain");
    }

    @Async
    @Override
    public void sendHtmlBroadcastEmail(List<String> to, String subject, String content) {
        sendEmail(new MimeMessage(session), to, subject, content, "text/html");
    }

    @Override
    public void sendTextPlainEmail(String replyTo, String to, String subject, String content){
        sendEmail(getMimeMessage(replyTo, to), Collections.singletonList(to), subject, content, "text/plain");
    }

    @Override
    public void sendHtmlEmail(String replyTo, String to, String subject, String content){
        sendEmail(getMimeMessage(replyTo, to), Collections.singletonList(to), subject, content, "text/html");
    }

    private void sendEmail(Message message, List<String> to, String subject, String content, String contentType) {
        try {
            for (String destination : to) {
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(destination));
            }
            message.setSubject(subject);
            message.setContent(content, contentType);
            Transport.send(message);
        }  catch(MessagingException mex) {
           mex.printStackTrace();
        }
    }

    private MimeMessage getMimeMessage(String replyTo, String to){
        try {
            MimeMessage message = new MimeMessage(session);
            String from = String.format("\"%s\" <%s>", replyTo, SERVER_MAIL);
            message.setFrom(new InternetAddress(from));
            message.setReplyTo(new Address[]{new InternetAddress(replyTo)});
            return message;
         } catch (MessagingException mex) {
        throw new RuntimeException(mex.getMessage());
    }

    }

}
