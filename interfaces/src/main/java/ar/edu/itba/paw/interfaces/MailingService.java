package ar.edu.itba.paw.interfaces;


import java.util.Collections;
import java.util.List;

public interface MailingService {

    /**
     * Attempts to send a broadcast email to a list of destintations. Using content type = text/plain
     * @param to  Set a list to send the email
     * @param subject The subject of the email
     * @param content The content of the email
     */
    void sendTextPlainBroadcastEmail(List<String> to, String subject, String content);

    /**
     * Attempts to send a broadcast email to a list of destintations. Using content type = text/html
     * @param to  Set a list to send the email
     * @param subject The subject of the email
     * @param content The content of the email
     */
    void sendHtmlBroadcastEmail(List<String> to, String subject, String content);

    /**
     * Attempts to send a email to "to" destintation. Using content type = text/plain
     * @param to  Set a list to send the email
     * @param subject The subject of the email
     * @param content The content of the email
     */
    void sendTextPlainEmail(String replyTo, String to, String subject, String content);

    /**
     * Attempts to send a email to "to" destintation. Using content type = text/plain
     * @param to  Set a list to send the email
     * @param subject The subject of the email
     * @param content The content of the email
     */
    void sendHtmlEmail(String replyTo, String to, String subject, String content);
}
