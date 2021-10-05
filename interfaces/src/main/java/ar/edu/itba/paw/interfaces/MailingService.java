package ar.edu.itba.paw.interfaces;


import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.User;

import java.util.List;

public interface MailingService {
    /**
     * Attempts to email "to" destination. Using content type = text/plain
     * @param student who sends the email
     * @param to    the destination
     * @param subject   The email subject
     * @param content   The email content
     * @param course    The course where the email is being send from
     */
    void sendTeacherEmail(User student, String to, String subject, String content, Course course);

    /**
     * Attempts to send a broadcast email to every student in course notifying of a new announcement
     *
     * @param to      list of students emails addresses in course
     * @param title   The title of the new announcement
     * @param content The content of the new announcement
     * @param course  The course where de announcement what published
     * @param author  The author of the announcement
     */
    void sendNewAnnouncementNotification(List<String> to, String title, String content, Course course, User author);

}
