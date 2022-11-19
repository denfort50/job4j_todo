package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;

import java.util.List;
import java.util.Map;

/**
 * Класс представляет собой реализацию доступа к хранилищу задач с помощью Hibernate
 * @author Denis Kalchenko
 * @version 1.0
 */
@Repository
@AllArgsConstructor
public class HibernateTaskRepository implements TaskRepository {

    private static final String COMPLETE_TASK = "UPDATE Task t SET t.done = :fDone WHERE t.id = :fId";
    private static final String UPDATE_TASK = "UPDATE Task t SET t.title = :fTitle, t.description = :fDescription WHERE t.id = :fId";
    private static final String DELETE_TASK = "DELETE Task t WHERE t.id = :fId";
    private static final String SELECT_ALL_TASKS = "FROM Task";
    private static final String SELECT_TASK_BY_STATUS = "FROM Task t WHERE t.done = :fDone";
    private static final String SELECT_TASK_BY_ID = "FROM Task t WHERE t.id = :fId";
    private static final String DELETE_ALL_TASKS = "DELETE Task";

    private CrudRepository crudRepository;

    /** Объект для логирования событий */
    private static final Logger LOG = LogManager.getLogger(HibernateTaskRepository.class.getName());

    public Task add(Task task) {
        crudRepository.run(session -> session.save(task));
        return task;
    }

    public boolean complete(int id) {
        return crudRepository.queryAndGetBoolean(COMPLETE_TASK, Map.of("fDone", true, "fId", id));
    }

    public boolean update(Task task) {
        return crudRepository.queryAndGetBoolean(UPDATE_TASK, Map.of(
                "fTitle", task.getTitle(),
                "fDescription", task.getDescription(),
                "fId", task.getId()));
    }

    public boolean delete(int id) {
        return crudRepository.queryAndGetBoolean(DELETE_TASK, Map.of("fId", id));
    }

    public List<Task> findAll() {
        return crudRepository.queryAndGetList(SELECT_ALL_TASKS, Task.class);
    }

    public List<Task> findTasksByStatus(boolean done) {
        return crudRepository.queryAndGetList(SELECT_TASK_BY_STATUS, Task.class, Map.of("fDone", done));
    }

    public Task findById(int id) {
        return (Task) crudRepository.queryAndGetObject(SELECT_TASK_BY_ID, Task.class, Map.of("fId", id));
    }

    public boolean deleteAll() {
        return crudRepository.queryAndGetBoolean(DELETE_ALL_TASKS);
    }
}
