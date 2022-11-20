package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

@AllArgsConstructor
public class CrudRepositoryImpl implements CrudRepository {

    private final SessionFactory sessionFactory;

    private static final Logger LOG = LogManager.getLogger(HibernateTaskRepository.class.getName());

    public void run(Consumer<Session> command) {
        executeTransaction(session -> {
                    command.accept(session);
                    return null;
                }
        );
    }

    public void run(String query) {
        Consumer<Session> command = session -> {
            var sq = session
                    .createQuery(query);
            sq.executeUpdate();
        };
        run(command);
    }

    public <T> List<T> queryAndGetList(String query, Class<T> tClass) {
        Function<Session, List<T>> command = session -> session
                .createQuery(query, tClass)
                .list();
        return executeTransaction(command);
    }

    public <T> List<T> queryAndGetList(String query, Class<T> tClass, Map<String, Object> args) {
        Function<Session, List<T>> command = session -> {
            var sq = session
                    .createQuery(query, tClass);
            for (Map.Entry<String, Object> arg : args.entrySet()) {
                sq.setParameter(arg.getKey(), arg.getValue());
            }
            return sq.list();
        };
        return executeTransaction(command);
    }

    public <T> Optional<T> queryAndGetOptional(String query, Class<T> tClass, Map<String, Object> args) {
        Function<Session, Optional<T>> command = session -> {
            var sq = session
                    .createQuery(query, tClass);
            for (Map.Entry<String, Object> arg : args.entrySet()) {
                sq.setParameter(arg.getKey(), arg.getValue());
            }
            return Optional.ofNullable(sq.getSingleResult());
        };
        return executeTransaction(command);
    }

    public <T> boolean queryAndGetBoolean(String query, Map<String, Object> args) {
        int intResult;
        Function<Session, Integer> command = session -> {
            var sq = session
                    .createQuery(query);
            for (Map.Entry<String, Object> arg : args.entrySet()) {
                sq.setParameter(arg.getKey(), arg.getValue());
            }
            return sq.executeUpdate();
        };
        intResult = executeTransaction(command);
        return intResult > 0;
    }

    public <T> boolean queryAndGetBoolean(String query) {
        int intResult;
        Function<Session, Integer> command = session -> {
            var sq = session
                    .createQuery(query);
            return sq.executeUpdate();
        };
        intResult = executeTransaction(command);
        return intResult > 0;
    }

    public <T> Object queryAndGetObject(String query, Class<T> tClass, Map<String, Object> args) {
        Function<Session, Object> command = session -> {
            var sq = session
                    .createQuery(query, tClass);
            for (Map.Entry<String, Object> arg : args.entrySet()) {
                sq.setParameter(arg.getKey(), arg.getValue());
            }
            return sq.uniqueResult();
        };
        return executeTransaction(command);
    }

    public <T> T executeTransaction(Function<Session, T> command) {
        var session = sessionFactory.openSession();
        try {
            var transaction = session.beginTransaction();
            T rsl = command.apply(session);
            transaction.commit();
            return rsl;
        } catch (Exception exception) {
            var transaction = session.getTransaction();
            if (transaction.isActive()) {
                transaction.rollback();
            }
            LOG.error("Exception", exception);
            throw exception;
        } finally {
            session.close();
        }
    }
}
