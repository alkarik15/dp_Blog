package ru.skillbox.blog.service.impl;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skillbox.blog.dto.LoginDto;
import ru.skillbox.blog.dto.ResultLoginDto;
import ru.skillbox.blog.dto.ResultsDto;
import ru.skillbox.blog.dto.UserLoginDto;
import ru.skillbox.blog.dto.UserRegisterDto;
import ru.skillbox.blog.model.CaptchaCodeEntity;
import ru.skillbox.blog.model.UserEntity;
import ru.skillbox.blog.model.enums.ModerationStatus;
import ru.skillbox.blog.repository.PostsRepository;
import ru.skillbox.blog.repository.UsersRepository;
import ru.skillbox.blog.service.CaptchaService;
import ru.skillbox.blog.service.UserService;

/**
 * @author alkarik
 * @link http://alkarik
 */
@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PostsRepository postsRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CaptchaService captchaService;

    @Override
    public boolean existEmail(final String email) {
        return usersRepository.existsByEmail(email);
    }

    @Override
    @Transactional(readOnly = false)
    public void addUser(final UserEntity user) {
        usersRepository.save(user);
    }

    @Override
    public UserEntity findEmail(final String email) {
        return usersRepository.findByEmail(email);
    }

    @Override
    public ResultLoginDto findUserByEmailAndPassword(LoginDto loginDto) {
        final UserEntity user = usersRepository.findByEmailAndPassword(loginDto.getE_mail(), loginDto.getPassword());
        ResultLoginDto resultLoginDto = new ResultLoginDto();
        if (user != null) {
            UserLoginDto userLoginDto = modelMapper.map(user, UserLoginDto.class);
            resultLoginDto.setUserLoginDto(userLoginDto);
        }
        return resultLoginDto;
    }

    @Override
    public Boolean isModerator(final Integer userId) {
        UserEntity userEntity = usersRepository.findAllById(userId);
        return userEntity.getIsModerator();
    }

    @Override
    public UserEntity findUserById(final Integer userId) {
        return usersRepository.findAllById(userId);
    }

    @Override
    public UserLoginDto getUserLoginDto(final Integer userId) {
        final UserEntity userEntity = usersRepository.findAllById(userId);

        UserLoginDto userDto = modelMapper.map(userEntity, UserLoginDto.class);
        userDto.setModeration(userEntity.getIsModerator());
        userDto.setSettings(userEntity.getIsModerator());

        userDto.setModerationCount(postsRepository.countAllByIsActiveAndModerationStatus(true, ModerationStatus.NEW));
        return userDto;
    }

    @Override
    @Transactional(readOnly = false)
    public ResultsDto createUser(UserRegisterDto userRegister) {
        Map<String, String> errors;
        //пока нет имени будет почта
        userRegister.setName(userRegister.getEmail());
        errors = validateRegistration(userRegister);

        ResultsDto result = new ResultsDto();
        if (errors.size() == 0) {
            //пока нет имени будет почта
            addUser(new UserEntity(LocalDateTime.now(), userRegister.getName(), userRegister.getEmail(), userRegister.getPassword()));
            result.setResult(true);
        } else {
            result.setResult(false);
            result.setErrors(errors);
        }
        return result;
    }

    public Map<String, String> validateRegistration(final UserRegisterDto userRegister) {
        Map<String, String> errors = new HashMap<>();
        if (userRegister.getName() == null || userRegister.getName().length() == 0) {
            errors.put("name", "Имя указано не верно");
        }
        if (userRegister.getPassword().length() < 6) {
            errors.put("password", "Пароль короче 6-ти символов");
        }
        if (existEmail(userRegister.getEmail())) {
            errors.put("email", "Этот e-mail уже зарегистрирован");
        }
        if (errors.size() == 0) {
            final CaptchaCodeEntity captcha = captchaService.findCaptcha(userRegister.getCaptcha(), userRegister.getCaptchaSecret());
            if (captcha == null) {
                errors.put("captcha", "Код с картинки введен не верно");
            } else {
                captchaService.deleteCaptcha(captcha);
            }
        }
        return errors;
    }
//    public Integer getUserIdFromSession(HttpServletRequest request) {
//        if (request.getSession().getAttribute("user") != null && request.getSession().getAttribute("user").toString().length() > 0) {
//            Integer userId = Integer.parseInt(request.getSession().getAttribute("user").toString());
//            return userId;
//        }
//        throw new UserUnauthorizedException("User unauthorized");
//    }
}
