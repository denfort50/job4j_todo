package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.User;

import java.util.Optional;

/**
 * Класс представляет собой реализацию доступа к хранилищу пользователей с помощью Hibernate
 *
 * @author Denis Kalchenko
 * @version 1.0
 */
@Repository
@AllArgsConstructor
public class HibernateUserRepository implements UserRepository {

    private static final String SELECT_USER_BY_LOGIN_AND_PASSWORD = "FROM User u WHERE u.login = :fLogin AND u.password = :fPassword";
    private static final String SELECT_USER_BY_ID = "FROM User u WHERE u.id = :fId";
    private static final String DELETE_ALL_USERS = "DELETE User";

    /**
     * Взаимодействие с базой данных происходит за счет объекта SessionFactory
     */
    private final SessionFactory sf;

    /**
     * Объект для логирования событий
     */
    private static final Logger LOG = LogManager.getLogger(HibernateTaskRepository.class.getName());

    @Override
    public Optional<User> add(User user) {
        Optional<User> result = Optional.empty();
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
            result = Optional.of(user);
        } catch (Exception e) {
            session.getTransaction().rollback();
            LOG.error("Exception", e);
        }
        session.close();
        return result;
    }

    @Override
    public Optional<User> findUserByLoginAndPassword(String login, String password) {
        Optional<User> result;
        Session session = sf.openSession();
        session.beginTransaction();
        result = session.createQuery(SELECT_USER_BY_LOGIN_AND_PASSWORD, User.class)
                .setParameter("fLogin", login)
                .setParameter("fPassword", password)
                .uniqueResultOptional();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    @Override
    public Optional<User> findUserById(int userId) {
        Optional<User> result;
        Session session = sf.openSession();
        session.beginTransaction();
        result = session.createQuery(SELECT_USER_BY_ID, User.class)
                .setParameter("fId", userId)
                .uniqueResultOptional();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    @Override
    public boolean deleteAll() {
        int intResult = 0;
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            intResult = session.createQuery(DELETE_ALL_USERS).executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            LOG.error("Exception", e);
        }
        session.close();
        return intResult > 0;
    }
}
