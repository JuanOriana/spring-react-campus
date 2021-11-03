package ar.edu.itba.paw.interfaces;


import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.User;

import java.util.List;

public interface MailingService {
    /**
     * Attempts to email "to" destination. Using content type = text/plain
     * @param sender        who sends the email
     * @param recipientId   the mail receiver
     * @param subject       The email subject
     * @param content       The email content
     * @param courseId      The course where the email is being sent from
     */
    void sendEmail(User sender, Long recipientId, String subject, String content, Long courseId);

    /**
     * Attempts to send a broadcast email to every student in course notifying of a new announcement
     *  @param to      list of students emails addresses in course
     * @param title   The title of the new announcement
     * @param content The content of the new announcement
     * @param course  The course where de announcement what published
     * @param author  The author of the announcement
     */
    void broadcastAnnouncementNotification(List<String> to, String title, String content, Course course, User author, String baseUrl);

}
