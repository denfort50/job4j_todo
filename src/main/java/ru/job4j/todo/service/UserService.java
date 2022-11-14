package ru.job4j.todo.service;

import ru.job4j.todo.model.User;

import java.util.Optional;

/**
 * Интерфейс описывает методы сервиса, обеспечивающего доступ к хранилищу пользователей
 * @author Denis Kalchenko
 * @version 1.0
 */
public interface UserService {

    Optional<User> add(User user);

    Optional<User> findUserByLoginAndPassword(String login, String password);

    Optional<User> findUserById(int userId);

    boolean deleteAll();
}
