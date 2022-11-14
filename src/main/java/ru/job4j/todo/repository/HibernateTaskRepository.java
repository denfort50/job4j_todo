package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;

import java.util.List;

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

    /** Взаимодействие с базой данных происходит за счет объекта SessionFactory */
    private final SessionFactory sf;

    /** Объект для логирования событий */
    private static final Logger LOG = LogManager.getLogger(HibernateTaskRepository.class.getName());

    public Task add(Task task) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.save(task);
        session.getTransaction().commit();
        session.close();
        return task;
    }

    public boolean complete(int id) {
        int intResult = 0;
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            intResult = session.createQuery(COMPLETE_TASK)
                    .setParameter("fDone", true)
                    .setParameter("fId", id)
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            LOG.error("Exception", e);
        }
        session.close();
        return intResult > 0;
    }

    public boolean update(Task task) {
        int intResult = 0;
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            intResult = session.createQuery(UPDATE_TASK)
                    .setParameter("fTitle", task.getTitle())
                    .setParameter("fDescription", task.getDescription())
                    .setParameter("fId", task.getId())
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            LOG.error("Exception", e);
        }
        session.close();
        return intResult > 0;
    }

    public boolean delete(int id) {
        int intResult = 0;
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            intResult = session.createQuery(DELETE_TASK)
                    .setParameter("fId", id)
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            LOG.error("Exception", e);
        }
        session.close();
        return intResult > 0;
    }

    public List<Task> findAll() {
        Session session = sf.openSession();
        session.beginTransaction();
        List<Task> result = session.createQuery(SELECT_ALL_TASKS, Task.class).list();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    public List<Task> findTasksByStatus(boolean done) {
        Session session = sf.openSession();
        session.beginTransaction();
        List<Task> result = session.createQuery(SELECT_TASK_BY_STATUS, Task.class)
                .setParameter("fDone", done).list();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    public Task findById(int id) {
        Session session = sf.openSession();
        session.beginTransaction();
        Task result = session.createQuery(SELECT_TASK_BY_ID, Task.class)
                .setParameter("fId", id)
                .uniqueResult();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    public boolean deleteAll() {
        int intResult = 0;
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            intResult = session.createQuery(DELETE_ALL_TASKS).executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            LOG.error("Exception", e);
        }
        session.close();
        return intResult > 0;
    }

}
