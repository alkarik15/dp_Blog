package ru.skillbox.blog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.skillbox.blog.model.Users;
import ru.skillbox.blog.repository.UsersRepository;
import ru.skillbox.blog.service.UsersService;

/**
 * @author alkarik
 * @link http://alkarik
 */
@Service
public class UsersServiceImpl implements UsersService {
    @Autowired
    private UsersRepository usersRepository;

    @Override
    public boolean existEmail(final String email) {
        return usersRepository.existsByEmail(email);
    }

    @Override
    public void addUser(final Users user) {
        usersRepository.save(user);
    }

    @Override
    public Users findEmail(final String email) {
        return usersRepository.findByEmail(email);
    }
}
