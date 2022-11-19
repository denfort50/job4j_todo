package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.User;
import ru.job4j.todo.repository.UserRepository;

import java.util.Optional;

/**
 * Класс представляет собой реализацию сервиса для доступа к хранилищу пользователей
 * @author Denis Kalchenko
 * @version 1.0
 */
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public Optional<User> add(User user) {
        return userRepository.add(user);
    }

    @Override
    public Optional<User> findUserByLoginAndPassword(String login, String password) {
        return userRepository.findUserByLoginAndPassword(login, password);
    }

    @Override
    public Optional<User> findUserById(int userId) {
        return userRepository.findUserById(userId);
    }

    @Override
    public void deleteAll() {
        userRepository.deleteAll();
    }
}
