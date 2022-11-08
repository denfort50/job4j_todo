package ru.job4j.todo.repository;

import ru.job4j.todo.model.Task;

import java.util.List;

/**
 * Интерфейс описывает методы взаимодействия с хранилищем задач
 * @author Denis Kalchenko
 * @version 1.0
 */
public interface TaskRepository {

    /**
     * Метод сохраняет задачу
     * @param task задача
     * @return возвращает задачу
     */
    Task add(Task task);

    /**
     * Метод позволяет отметить задачу как выполненную
     * @param id идентификатор задачи
     * @return возвращает true, если отметка о выполнении задачи выставлена успешно
     */
    boolean complete(int id);

    /**
     * Метод обновляет задачу
     * @param task задача
     * @return возвращает true, если обновление задачи выполнено успешно
     */
    boolean update(Task task);

    /**
     * Метод удаляет задачу
     * @param id идентификатор задачи
     * @return возвращает true, если удаление задачи выполнено успешно
     */
    boolean delete(int id);

    /**
     * Метод позволяет получить список всех задач
     * @return возвращает список всех задач
     */
    List<Task> findAll();

    /**
     * Метод позволяет получить список новых задач
     * @return возвращает список новых задач
     */
    List<Task> findNew();

    /**
     * Метод позволяет получить список выполненных задач
     * @return возвращает список выполненных задач
     */
    List<Task> findCompleted();

    /**
     * Метод позволяет получить задачу по идентификатору
     * @param id идентификатор
     * @return возвращает задачу
     */
    Task findById(int id);

    /**
     * Метод удаляет все задачи
     * @return возвращает true, если удаление задач выполнено успешно
     */
    boolean deleteAll();
}
