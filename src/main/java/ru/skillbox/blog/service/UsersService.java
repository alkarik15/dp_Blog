package ru.skillbox.blog.service;

import ru.skillbox.blog.model.Users;

/**
 * @author alkarik
 * @link http://alkarik
 */
public interface UsersService {

    boolean existEmail(String email);

    Users findEmail(String email);

    void addUser(Users user);
}
