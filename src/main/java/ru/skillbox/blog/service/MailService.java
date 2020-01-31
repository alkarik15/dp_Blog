package ru.skillbox.blog.service;

import javax.servlet.http.HttpServletRequest;

/**
 * @author alkarik
 * @link http://alkarik
 */
public interface MailService {
    Boolean sendEmail(HttpServletRequest request, String email);
}
