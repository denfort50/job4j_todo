package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.User;

import java.util.Map;
import java.util.Optional;

/**
 * Класс представляет собой реализацию доступа к хранилищу пользователей с помощью Hibernate
 * @author Denis Kalchenko
 * @version 1.0
 */
@Repository
@AllArgsConstructor
public class HibernateUserRepository implements UserRepository {

    private static final String SELECT_USER_BY_LOGIN_AND_PASSWORD = "FROM User u WHERE u.login = :fLogin AND u.password = :fPassword";
    private static final String SELECT_USER_BY_ID = "FROM User u WHERE u.id = :fId";
    private static final String DELETE_ALL_USERS = "DELETE User";

    private CrudRepository crudRepository;

    @Override
    public Optional<User> add(User user) {
        crudRepository.run(session -> session.save(user));
        return Optional.of(user);
    }

    @Override
    public Optional<User> findUserByLoginAndPassword(String login, String password) {
        return crudRepository.queryAndGetOptional(SELECT_USER_BY_LOGIN_AND_PASSWORD, User.class,
                Map.of("fLogin", login, "fPassword", password));
    }

    @Override
    public Optional<User> findUserById(int userId) {
        return crudRepository.queryAndGetOptional(SELECT_USER_BY_ID, User.class,
                Map.of("fId", userId));
    }

    @Override
    public void deleteAll() {
        crudRepository.run(DELETE_ALL_USERS);
    }
}
