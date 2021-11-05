package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.CourseService;
import ar.edu.itba.paw.interfaces.MailingService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.exception.CourseNotFoundException;
import ar.edu.itba.paw.models.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class MailingServiceImpl implements MailingService {

    private final Session session;
    private final SpringTemplateEngine templateEngine;
    private final CourseService courseService;
    private final UserService userService;
    private final String SERVER_MAIL = "mpvcampus@gmail.com";

    @Autowired
    private MessageSource messageSource;

    @Autowired
    public MailingServiceImpl(Session session, SpringTemplateEngine templateEngine,
                              UserService userService, CourseService courseService) {
        this.session = session;
        this.templateEngine = templateEngine;
        this.courseService = courseService;
        this.userService = userService;
    }


    @Override
    public void sendEmail(User sender, Long receiverId, String subject, String content, Long courseId, Locale locale) {
        User user = userService.findById(receiverId).orElseThrow(UserNotFoundException::new);
        Course course = courseService.findById(courseId).orElseThrow(CourseNotFoundException::new);
        Map<String,Object> model = new HashMap<>();
        model.put("subjectName", course.getSubject().getName());
        model.put("student", sender);
        model.put("subject", subject);
        model.put("content", content);
        model.put("year", String.valueOf(LocalDateTime.now().getYear()));
        sendThymeleafTemplateEmail(getMimeMessage(sender.getEmail()), Collections.singletonList(user.getEmail()),
                subject, model, "student-email-to-teacher.html", locale);
    }


    @Override
    @Async
    public void broadcastAnnouncementNotification(List<String> to, String title, String content, Course course, User author, String url, Locale locale) {
        Map<String, Object> model = new HashMap<>();
        model.put("title", title);
        model.put("content", content);
        model.put("subjectName", course.getSubject().getName());
        model.put("year", String.valueOf(LocalDateTime.now().getYear()));
        model.put("url", url);
        model.put("courseId", course.getCourseId());
        model.put("author", author);
        sendThymeleafTemplateEmail(to, messageSource.getMessage( "mail.new.announcement.title", null, "", locale) + ": " + course.getSubject().getName(), model, "new-announcement-notification.html", locale);
    }

    private void transportMessage(Message message, List<String> to, String subject, String content, String contentType) {
        try {
            for (String destination : to) {
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(destination));
            }
            message.setSubject(subject);
            message.setContent(content, contentType);
            Transport.send(message);
        } catch (MessagingException ignored) {
        }
    }

    private MimeMessage getMimeMessage(String replyTo) {
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

    private void sendThymeleafTemplateEmail(List<String> to, String subject, Map<String, Object> args, String templateName, Locale locale) {
        Context context = new Context(locale);
        context.setVariables(args);
        String htmlBody = templateEngine.process(templateName, context);
        sendHtmlBroadcastEmail(to, subject, htmlBody);
    }

    private void sendHtmlBroadcastEmail(List<String> to, String subject, String content) {
        transportMessage(new MimeMessage(session), to, subject, content, "text/html;charset=\"UTF-8\"");
    }

    private void sendThymeleafTemplateEmail(Message message,List<String> to,String subject, Map<String, Object> args, String templateName, Locale locale) {
        Context context = new Context(locale);
        context.setVariables(args);
        String htmlBody = templateEngine.process(templateName, context);
        transportMessage(message,to,subject,htmlBody, "text/html;charset=\"UTF-8\"");
    }
}
