package ru.skillbox.blog.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skillbox.blog.dto.LoginDto;
import ru.skillbox.blog.dto.ResultLoginDto;
import ru.skillbox.blog.dto.UserLoginDto;
import ru.skillbox.blog.model.UserEntity;
import ru.skillbox.blog.repository.UsersRepository;
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
    private ModelMapper modelMapper;

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
        final UserEntity user = usersRepository.findByEmailAndPassword(loginDto.getEmail(), loginDto.getPassword());
        ResultLoginDto resultLoginDto = new ResultLoginDto();
        if (user != null) {
            UserLoginDto userLoginDto = modelMapper.map(user, UserLoginDto.class);
            resultLoginDto.setUserLoginDto(userLoginDto);
        }
        return resultLoginDto;
    }
}
