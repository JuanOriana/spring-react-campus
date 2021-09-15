package ar.edu.itba.paw.interfaces;


import java.util.List;

public interface MailingService {

    /**
     * Attempts to send a email to "to" destintation with the server mail
     * @param replyTo The user that send the email
     * @param to  Set the destination
     * @param subject The subject of the email
     * @param content The content of the email
     * @param contentType The content type of the email. e.j text/plain or text/html
     */
    void sendEmail(String replyTo, String to, String subject, String content, String contentType);

    /**
     * Attempts to send a email to "to" destintation with the server mail
     * @param to  Set a list to send the email
     * @param subject The subject of the email
     * @param content The content of the email
     * @param contentType The content type of the email. e.j text/plain or text/html
     */
    void sendBroadcastEmail(List<String> to, String subject, String content, String contentType);
}
