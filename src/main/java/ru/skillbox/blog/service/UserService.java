package ru.skillbox.blog.service;

import ru.skillbox.blog.dto.LoginDto;
import ru.skillbox.blog.dto.ResultLoginDto;
import ru.skillbox.blog.dto.ResultsDto;
import ru.skillbox.blog.dto.UserLoginDto;
import ru.skillbox.blog.dto.UserRegisterDto;
import ru.skillbox.blog.model.UserEntity;

/**
 * @author alkarik
 * @link http://alkarik
 */
public interface UserService {
    void addUser(UserEntity user);

    boolean existEmail(String email);

    UserEntity findEmail(String email);

    ResultLoginDto findUserByEmailAndPassword(LoginDto loginDto);

    Boolean isModerator(Integer userId);

    UserEntity findUserById(Integer userId);

    UserLoginDto getUserLoginDto(final Integer userId);

    ResultsDto createUser(UserRegisterDto userRegister);

//    Integer getUserIdFromSession(HttpServletRequest request);
}
