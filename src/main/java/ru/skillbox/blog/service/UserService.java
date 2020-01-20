package ru.skillbox.blog.service;

import ru.skillbox.blog.model.UserEntity;

/**
 * @author alkarik
 * @link http://alkarik
 */
public interface UserService {

    boolean existEmail(String email);

    UserEntity findEmail(String email);

    void addUser(UserEntity user);
}
