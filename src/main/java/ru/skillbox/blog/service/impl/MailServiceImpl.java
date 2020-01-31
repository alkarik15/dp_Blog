package ru.skillbox.blog.service.impl;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import ru.skillbox.blog.model.UserEntity;
import ru.skillbox.blog.service.MailService;
import ru.skillbox.blog.service.UserService;
import ru.skillbox.blog.utils.MailConstructor;

/**
 * @author alkarik
 * @link http://alkarik
 */
@Service
public class MailServiceImpl implements MailService {
    @Autowired
    private UserService userService;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private MailConstructor mailConstructor;

    public Boolean sendEmail(final HttpServletRequest request, @RequestBody final String email) {
        final UserEntity user = userService.findEmail(email);
        if (user != null) {
            String token = UUID.randomUUID().toString();
            String appUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
            SimpleMailMessage newEmail = mailConstructor.constructResetPassword(appUrl, user.getEmail(), token);
            mailSender.send(newEmail);
            return true;
        } else {
            return false;
        }
    }
}
