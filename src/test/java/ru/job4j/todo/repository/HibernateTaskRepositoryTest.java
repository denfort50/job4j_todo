package ru.job4j.todo.repository;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.model.Priority;
import ru.job4j.todo.model.Task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;
import static ru.job4j.todo.util.SessionFactoryLoader.getSessionFactory;

class HibernateTaskRepositoryTest {

    private final SessionFactory sessionFactory = getSessionFactory();

    private final CrudRepository crudRepository = new CrudRepositoryImpl(sessionFactory);

    private final TaskRepository taskRepository = new HibernateTaskRepository(crudRepository);

    @AfterEach
    void cleanTable() {
        taskRepository.deleteAll();
    }

    @Test
    void whenFindByIdThenSuccess() {
        Task task1 = new Task(1, "Задача 1", "Описание 1", new Priority(1, "Критический", 1));
        taskRepository.add(task1);
        assertThat(taskRepository.findById(task1.getId()).getTitle()).isEqualTo(task1.getTitle());
    }

    @Test
    void whenAddThenSuccess() {
        Task task1 = new Task(1, "Задача 1", "Описание 1", new Priority(1, "Критический", 1));
        taskRepository.add(task1);
        Task taskInDB = taskRepository.findById(task1.getId());
        assertThat(taskInDB.getTitle()).isEqualTo(task1.getTitle());
    }

    @Test
    void whenCompleteThenSuccess() {
        Task task1 = new Task(1, "Задача 1", "Описание 1", new Priority(1, "Критический", 1));
        taskRepository.add(task1);
        taskRepository.complete(task1.getId());
        Task taskInDB = taskRepository.findById(task1.getId());
        assertThat(taskInDB.isDone()).isTrue();
    }

    @Test
    void whenUpdateThenSuccess() {
        Task task1 = new Task(1, "Задача 1", "Описание 1", new Priority(1, "Критический", 1));
        taskRepository.add(task1);
        task1.setDescription("Описание 2");
        taskRepository.update(task1);
        assertThat(taskRepository.findById(task1.getId()).getDescription()).isEqualTo("Описание 2");
    }

    @Test
    void whenDeleteThenSuccess() {
        Task task1 = new Task(1, "Задача 1", "Описание 1", new Priority(1, "Критический", 1));
        taskRepository.add(task1);
        taskRepository.delete(task1.getId());
        assertThat(taskRepository.findById(task1.getId())).isNull();
    }

    @Test
    void whenFindAllThenSuccess() {
        taskRepository.deleteAll();
        Task task1 = new Task(1, "Задача 1", "Описание 1", new Priority(1, "Критический", 1));
        Task task2 = new Task(2, "Задача 2", "Описание 2", new Priority(1, "Критический", 1));
        List<Category> categories = new ArrayList<>();
        categories.add(new Category(1, "Общие"));
        task1.setCategories(categories);
        task2.setCategories(categories);
        taskRepository.add(task1);
        taskRepository.add(task2);
        List<Task> tasksInDB = taskRepository.findAll();
        assertThat(tasksInDB.stream().map(Task::getId).collect(Collectors.toList()))
                .containsExactly(task1.getId(), task2.getId());
    }

    @Test
    void whenFindNewThenSuccess() {
        Task task1 = new Task(1, "Задача 1", "Описание 1", new Priority(1, "Критический", 1));
        Task task2 = new Task(2, "Задача 2", "Описание 2", new Priority(1, "Критический", 1));
        Task task3 = new Task(3, "Задача 3", "Описание 3", new Priority(1, "Критический", 1));
        List<Category> categories = new ArrayList<>();
        categories.add(new Category(1, "Общие"));
        task1.setCategories(categories);
        task2.setCategories(categories);
        task3.setCategories(categories);
        taskRepository.add(task1);
        taskRepository.add(task2);
        taskRepository.add(task3);
        taskRepository.complete(task1.getId());
        List<Task> tasksInDB = taskRepository.findTasksByStatus(false);
        assertThat(tasksInDB.stream().map(Task::getId).collect(Collectors.toList()))
                .containsExactly(task2.getId(), task3.getId());
    }

    @Test
    void whenFindCompletedThenSuccess() {
        Task task1 = new Task(1, "Задача 1", "Описание 1", new Priority(1, "Критический", 1));
        Task task2 = new Task(2, "Задача 2", "Описание 2", new Priority(1, "Критический", 1));
        Task task3 = new Task(3, "Задача 3", "Описание 3", new Priority(1, "Критический", 1));
        List<Category> categories = new ArrayList<>();
        categories.add(new Category(1, "Общие"));
        task1.setCategories(categories);
        task2.setCategories(categories);
        task3.setCategories(categories);
        taskRepository.add(task1);
        taskRepository.add(task2);
        taskRepository.add(task3);
        taskRepository.complete(task1.getId());
        taskRepository.complete(task2.getId());
        List<Task> tasksInDB = taskRepository.findTasksByStatus(true);
        assertThat(tasksInDB.stream().map(Task::getId).collect(Collectors.toList()))
                .containsExactly(task1.getId(), task2.getId());
    }

    @Test
    void whenDeleteAllThenSuccess() {
        Task task1 = new Task(1, "Задача 1", "Описание 1", new Priority(1, "Критический", 1));
        Task task2 = new Task(2, "Задача 2", "Описание 2", new Priority(1, "Критический", 1));
        Task task3 = new Task(3, "Задача 3", "Описание 3", new Priority(1, "Критический", 1));
        List<Category> categories = new ArrayList<>();
        categories.add(new Category(1, "Общие"));
        task1.setCategories(categories);
        task2.setCategories(categories);
        task3.setCategories(categories);
        taskRepository.add(task1);
        taskRepository.add(task2);
        taskRepository.add(task3);
        taskRepository.deleteAll();
        assertThat(taskRepository.findAll()).isEmpty();
    }
}