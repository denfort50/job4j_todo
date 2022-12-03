package ru.job4j.todo.controller;

import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import ru.job4j.todo.model.Priority;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.service.PriorityService;
import ru.job4j.todo.service.PriorityServiceImpl;
import ru.job4j.todo.service.TaskServiceImpl;
import ru.job4j.todo.service.TaskService;

import javax.servlet.http.HttpSession;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskControllerTest {

    @Test
    void whenGetAllTasksThenSuccess() {
        Task task1 = new Task(1, "Задача 1", "Описание 1", new Priority());
        Task task2 = new Task(2, "Задача 2", "Описание 2", new Priority());
        List<Task> tasks = List.of(task1, task2);
        Model model = mock(Model.class);
        HttpSession session = mock(HttpSession.class);
        TaskService taskService = mock(TaskServiceImpl.class);
        PriorityService priorityService = mock(PriorityServiceImpl.class);
        when(taskService.findAll()).thenReturn(tasks);
        TaskController taskController = new TaskController(taskService, priorityService);
        String page = taskController.getAllTasks(model, session);
        verify(model).addAttribute("allTasks", tasks);
        assertThat(page).isEqualTo("allTasks");
    }

    @Test
    void whenGetNewTasksThenSuccess() {
        Task task1 = new Task(1, "Задача 1", "Описание 1", new Priority());
        Task task2 = new Task(2, "Задача 2", "Описание 2", new Priority());
        Task task3 = new Task(3, "Задача 3", "Описание 3", new Priority());
        task1.setDone(true);
        List<Task> newTasks = List.of(task2, task3);
        Model model = mock(Model.class);
        HttpSession session = mock(HttpSession.class);
        TaskService taskService = mock(TaskServiceImpl.class);
        PriorityService priorityService = mock(PriorityServiceImpl.class);
        when(taskService.findTasksByStatus(false)).thenReturn(newTasks);
        TaskController taskController = new TaskController(taskService, priorityService);
        String page = taskController.getNewTasks(model, session);
        verify(model).addAttribute("newTasks", newTasks);
        assertThat(page).isEqualTo("newTasks");
    }

    @Test
    void whenGetCompletedTasksThenSuccess() {
        Task task1 = new Task(1, "Задача 1", "Описание 1", new Priority());
        Task task2 = new Task(2, "Задача 2", "Описание 2", new Priority());
        Task task3 = new Task(3, "Задача 3", "Описание 3", new Priority());
        task1.setDone(true);
        task2.setDone(true);
        task3.setDone(true);
        List<Task> completedTasks = List.of(task1, task2, task3);
        Model model = mock(Model.class);
        HttpSession session = mock(HttpSession.class);
        TaskService taskService = mock(TaskServiceImpl.class);
        PriorityService priorityService = mock(PriorityServiceImpl.class);
        when(taskService.findTasksByStatus(true)).thenReturn(completedTasks);
        TaskController taskController = new TaskController(taskService, priorityService);
        String page = taskController.getCompletedTasks(model, session);
        verify(model).addAttribute("completedTasks", completedTasks);
        assertThat(page).isEqualTo("completedTasks");
    }

    @Test
    void whenAddTaskThenSuccess() {
        Task task1 = new Task(1, "Задача 1", "Описание 1", new Priority());
        Model model = mock(Model.class);
        TaskService taskService = mock(TaskServiceImpl.class);
        PriorityService priorityService = mock(PriorityServiceImpl.class);
        TaskController taskController = new TaskController(taskService, priorityService);
        taskService.add(task1);
        String page = taskController.addTask(model);
        verify(taskService).add(task1);
        assertThat(page).isEqualTo("addTask");
    }

    @Test
    void whenCreateTaskThenSuccess() {
        Task task1 = new Task(1, "Задача 1", "Описание 1", new Priority(1, "Критический", 1));
        TaskService taskService = mock(TaskServiceImpl.class);
        PriorityService priorityService = mock(PriorityServiceImpl.class);
        TaskController taskController = new TaskController(taskService, priorityService);
        HttpSession session = mock(HttpSession.class);
        when(priorityService.findById(task1.getPriority().getId())).thenReturn(task1.getPriority());
        String page = taskController.createTask(task1, session);
        verify(taskService).add(task1);
        assertThat(page).isEqualTo("redirect:/tasks");
    }

}