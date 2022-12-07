package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.repository.TaskRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Класс представляет собой реализацию сервиса для доступа к хранилищу объектов Task
 * @author Denis Kalchenko
 * @version 1.0
 */
@Service
@AllArgsConstructor
public class TaskServiceImpl implements TaskService {

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
        return taskRepository.findAll().stream().distinct().collect(Collectors.toList());
    }

    public List<Task> findTasksByStatus(boolean done) {
        return taskRepository.findTasksByStatus(done).stream().distinct().collect(Collectors.toList());
    }

    public Task findById(int id) {
        return taskRepository.findById(id);
    }

    public boolean deleteAll() {
        return taskRepository.deleteAll();
    }
}
