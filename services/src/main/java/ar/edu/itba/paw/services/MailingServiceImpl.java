package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.MailingService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Override
    public void sendEmail(String replyTo, String to, String subject, String content, String contentType) {
        try {
            MimeMessage message = new MimeMessage(session);

            String from = String.format("\"%s\" <%s>", replyTo, SERVER_MAIL);
            message.setFrom(new InternetAddress(from));
            message.setReplyTo(new Address[]{new InternetAddress(replyTo)});
            sendEmail(message, Collections.singletonList(to), subject, content, contentType);
        } catch (MessagingException mex) {
            throw new RuntimeException(mex.getMessage());
        }
    }

    @Override
    public void sendBroadcastEmail(List<String> to, String subject, String content, String contentType) {
        sendEmail(new MimeMessage(session), to, subject, content, contentType);
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


}
