package ru.job4j.todo.service;

import ru.job4j.todo.model.Task;

import java.util.List;

/**
 * Интерфейс описывает методы сервиса, обеспечивающего доступ к хранилищу объектов Task
 * @author Denis Kalchenko
 * @version 1.0
 */
public interface TaskService {

    Task add(Task task);

    boolean complete(int id);

    boolean update(Task task);

    boolean delete(int id);

    List<Task> findAll();

    List<Task> findTasksByStatus(boolean done);

    Task findById(int id);

    boolean deleteAll();
}
