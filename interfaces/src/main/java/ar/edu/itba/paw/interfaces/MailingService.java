package ar.edu.itba.paw.interfaces;


import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.User;

import java.util.List;

public interface MailingService {
    /**
     * Attempts to email "to" destination. Using content type = text/plain
     *
     * @param to      Set a list to send the email
     * @param subject The subject of the email
     * @param content The content of the email
     */
    void sendTextPlainEmail(String replyTo, String to, String subject, String content);

    /**
     * Attempts to send a broadcast email to every student in course notifying of a new announcement
     * @param to list of students emails addresses in course
     * @param title The title of the new announcement
     * @param content The content of the new announcement
     * @param course The course where de announcement what published
     * @param author The author of the announcement
     */
    void sendNewAnnouncementNotification(List<String> to,String title,String content, Course course, User author);

}
