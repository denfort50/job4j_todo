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
            intResult = session.createQuery("UPDATE Task t SET t.done = :fDone WHERE t.id = :fId")
                    .setParameter("fDone", true)
                    .setParameter("fId", id)
                    .executeUpdate();
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
            intResult = session.createQuery("UPDATE Task t SET t.title = :fTitle, t.description = :fDescription WHERE t.id = :fId")
                    .setParameter("fTitle", task.getTitle())
                    .setParameter("fDescription", task.getDescription())
                    .setParameter("fId", task.getId())
                    .executeUpdate();
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
            intResult = session.createQuery("DELETE Task t WHERE t.id = :fId")
                    .setParameter("fId", id)
                    .executeUpdate();
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
        List<Task> result = session.createQuery("FROM Task", Task.class).list();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    public List<Task> findNew() {
        Session session = sf.openSession();
        session.beginTransaction();
        List<Task> result = session.createQuery("FROM Task t WHERE t.done = false", Task.class).list();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    public List<Task> findCompleted() {
        Session session = sf.openSession();
        session.beginTransaction();
        List<Task> result = session.createQuery("FROM Task t WHERE t.done = true", Task.class).list();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    public Task findById(int id) {
        Session session = sf.openSession();
        session.beginTransaction();
        Task result = session.createQuery("FROM Task t WHERE t.id = :fId", Task.class)
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
            intResult = session.createQuery("DELETE Task").executeUpdate();
        } catch (Exception e) {
            session.getTransaction().rollback();
            LOG.error("Exception", e);
        }
        session.close();
        return intResult > 0;
    }

}
