package ru.job4j.todo.controller;

import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.service.TaskServiceImpl;
import ru.job4j.todo.service.TaskService;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskControllerTest {

    @Test
    void whenGetAllTasksThenSuccess() {
        Task task1 = new Task(1, "Задача 1", "Описание 1");
        Task task2 = new Task(2, "Задача 2", "Описание 2");
        List<Task> tasks = List.of(task1, task2);
        Model model = mock(Model.class);
        TaskService taskService = mock(TaskServiceImpl.class);
        when(taskService.findAll()).thenReturn(tasks);
        TaskController taskController = new TaskController(taskService);
        String page = taskController.getAllTasks(model);
        verify(model).addAttribute("allTasks", tasks);
        assertThat(page).isEqualTo("allTasks");
    }

    @Test
    void whenGetNewTasksThenSuccess() {
        Task task1 = new Task(1, "Задача 1", "Описание 1");
        Task task2 = new Task(2, "Задача 2", "Описание 2");
        Task task3 = new Task(3, "Задача 3", "Описание 3");
        task1.setDone(true);
        List<Task> newTasks = List.of(task2, task3);
        Model model = mock(Model.class);
        TaskService taskService = mock(TaskServiceImpl.class);
        when(taskService.findTasksByStatus(false)).thenReturn(newTasks);
        TaskController taskController = new TaskController(taskService);
        String page = taskController.getNewTasks(model);
        verify(model).addAttribute("newTasks", newTasks);
        assertThat(page).isEqualTo("newTasks");
    }

    @Test
    void whenGetCompletedTasksThenSuccess() {
        Task task1 = new Task(1, "Задача 1", "Описание 1");
        Task task2 = new Task(2, "Задача 2", "Описание 2");
        Task task3 = new Task(3, "Задача 3", "Описание 3");
        task1.setDone(true);
        task2.setDone(true);
        task3.setDone(true);
        List<Task> completedTasks = List.of(task1, task2, task3);
        Model model = mock(Model.class);
        TaskService taskService = mock(TaskServiceImpl.class);
        when(taskService.findTasksByStatus(true)).thenReturn(completedTasks);
        TaskController taskController = new TaskController(taskService);
        String page = taskController.getCompletedTasks(model);
        verify(model).addAttribute("completedTasks", completedTasks);
        assertThat(page).isEqualTo("completedTasks");
    }

    @Test
    void whenAddTaskThenSuccess() {
        Task task1 = new Task(1, "Задача 1", "Описание 1");
        Model model = mock(Model.class);
        TaskService taskService = mock(TaskServiceImpl.class);
        TaskController taskController = new TaskController(taskService);
        taskService.add(task1);
        String page = taskController.addTask(model);
        verify(taskService).add(task1);
        assertThat(page).isEqualTo("addTask");
    }

    @Test
    void whenCreateTaskThenSuccess() {
        Task task1 = new Task(1, "Задача 1", "Описание 1");
        TaskService taskService = mock(TaskServiceImpl.class);
        TaskController taskController = new TaskController(taskService);
        String page = taskController.createTask(task1);
        verify(taskService).add(task1);
        assertThat(page).isEqualTo("redirect:/tasks");
    }

}