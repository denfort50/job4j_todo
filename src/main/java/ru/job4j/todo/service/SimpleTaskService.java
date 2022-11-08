package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.repository.TaskRepository;

import java.util.List;

/**
 * Класс представляет собой реализацию сервиса для доступа к хранилищу объектов Task
 * @author Denis Kalchenko
 * @version 1.0
 */
@Service
@AllArgsConstructor
public class SimpleTaskService implements TaskService {

    /** Взаимодействие с хранилищем происходит через слой персистенции TaskRepository */
    private final TaskRepository taskRepository;

    public Task add(Task task) {
        return taskRepository.add(task);
    }

    public boolean complete(int id) {
        return taskRepository.complete(id);
    }

    public boolean update(Task task) {
        return taskRepository.update(task);
    }

    public boolean delete(int id) {
        return taskRepository.delete(id);
    }

    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    public List<Task> findNew() {
        return taskRepository.findNew();
    }

    public List<Task> findCompleted() {
        return taskRepository.findCompleted();
    }

    public Task findById(int id) {
        return taskRepository.findById(id);
    }

    public boolean deleteAll() {
        return taskRepository.deleteAll();
    }
}
