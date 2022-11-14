package ru.job4j.todo.repository;

import ru.job4j.todo.model.User;

import java.util.Optional;

/**
 * Интерфейс описывает методы взаимодействия с хранилищем пользователей
 * @author Denis Kalchenko
 * @version 1.0
 */
public interface UserRepository {

    /**
     * Метод сохраняет пользователя
     * @param user пользователь
     * @return возвращает пользователя
     */
    Optional<User> add(User user);

    /**
     * Метод находит пользователя по email и паролю
     * @param login почта
     * @param password пароль
     * @return возвращает пользователя
     */
    Optional<User> findUserByLoginAndPassword(String login, String password);

    /**
     * Метод находит пользователя по id
     * @param userId идентификатор
     * @return возвращает пользователя
     */
    Optional<User> findUserById(int userId);

    /**
     * Метод удаляет всех пользователей
     */
    boolean deleteAll();
}
