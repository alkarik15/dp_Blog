package ru.skillbox.blog.utils;

import java.util.Properties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

/**
 * @author alkarik
 * @link http://alkarik
 */
@Component
public class MailConstructor {
    @Value("${support.email}")
    private String supportEmail;

    public SimpleMailMessage constructResetPassword(String strUrl, String userEmail, String hash) {
        String url = strUrl + "/login/change-password/" + hash;
        String message = "\n Please click on this link to reset password";
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(userEmail);
        email.setSubject("Blog - reset password");
        email.setText(url + message);
        email.setFrom(supportEmail);
        return email;
    }

    @Bean
    public JavaMailSender javaMailService() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost("mail.hiik.ru");
        javaMailSender.setPort(25);
        javaMailSender.setJavaMailProperties(getMailProperties());

        return javaMailSender;
    }

    private Properties getMailProperties() {
        Properties properties = new Properties();
        properties.setProperty("mail.transport.protocol", "smtp");
        properties.setProperty("mail.smtp.auth", "false");
        properties.setProperty("mail.smtp.starttls.enable", "false");
        properties.setProperty("mail.debug", "false");
        return properties;
    }

}
