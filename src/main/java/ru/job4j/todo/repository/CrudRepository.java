package ru.job4j.todo.repository;

import org.hibernate.Session;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public interface CrudRepository {

    void run(Consumer<Session> command);

    void run(String query);

    <T> List<T> queryAndGetList(String query, Class<T> tClass);

    <T> List<T> queryAndGetList(String query, Class<T> tClass, Map<String, Object> args);

    <T> Optional<T> queryAndGetOptional(String query, Class<T> tClass, Map<String, Object> args);

    <T> boolean queryAndGetBoolean(String query);

    <T> boolean queryAndGetBoolean(String query, Map<String, Object> args);

    <T> Object queryAndGetObject(String query, Class<T> tClass, Map<String, Object> args);

    <T> T executeTransaction(Function<Session, T> command);
}
