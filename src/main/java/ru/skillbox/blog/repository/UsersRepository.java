package ru.skillbox.blog.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.skillbox.blog.model.Users;

/**
 * @author alkarik
 * @link http://alkarik
 */
@Repository
public interface UsersRepository extends CrudRepository<Users, Integer> {
    boolean existsByEmail(String email);

    Users findByEmail(String email);
}
