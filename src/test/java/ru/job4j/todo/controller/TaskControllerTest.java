package ru.job4j.todo.controller;

import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.model.Priority;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;
import ru.job4j.todo.service.*;
import ru.job4j.todo.util.UserAttributeTool;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

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
        CategoryService categoryService = mock(CategoryService.class);
        when(taskService.findAll()).thenReturn(tasks);
        User user = new User(1, "Denis", "mr_bond", "password", TimeZone.getDefault());
        when(UserAttributeTool.getAttributeUser(session)).thenReturn(user);
        TaskController taskController = new TaskController(taskService, priorityService, categoryService);
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
        CategoryService categoryService = mock(CategoryService.class);
        when(taskService.findTasksByStatus(false)).thenReturn(newTasks);
        User user = new User(1, "Denis", "mr_bond", "password", TimeZone.getDefault());
        when(UserAttributeTool.getAttributeUser(session)).thenReturn(user);
        TaskController taskController = new TaskController(taskService, priorityService, categoryService);
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
        CategoryService categoryService = mock(CategoryService.class);
        when(taskService.findTasksByStatus(true)).thenReturn(completedTasks);
        User user = new User(1, "Denis", "mr_bond", "password", TimeZone.getDefault());
        when(UserAttributeTool.getAttributeUser(session)).thenReturn(user);
        TaskController taskController = new TaskController(taskService, priorityService, categoryService);
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
        CategoryService categoryService = mock(CategoryService.class);
        TaskController taskController = new TaskController(taskService, priorityService, categoryService);
        taskService.add(task1);
        String page = taskController.addTask(model);
        verify(taskService).add(task1);
        assertThat(page).isEqualTo("addTask");
    }

    @Test
    void whenCreateTaskThenSuccess() {
        Task task1 = new Task(1, "Задача 1", "Описание 1", new Priority(1, "Критический", 1));
        List<Category> categories = List.of(new Category(1, "Общее"));
        TaskService taskService = mock(TaskServiceImpl.class);
        PriorityService priorityService = mock(PriorityServiceImpl.class);
        CategoryService categoryService = mock(CategoryService.class);
        TaskController taskController = new TaskController(taskService, priorityService, categoryService);
        HttpSession session = mock(HttpSession.class);
        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
        when(priorityService.findById(task1.getPriority().getId())).thenReturn(task1.getPriority());
        when(categoryService.getCategories(httpServletRequest)).thenReturn(categories);
        String page = taskController.createTask(task1, session, httpServletRequest);
        verify(taskService).add(task1);
        assertThat(page).isEqualTo("redirect:/tasks");
    }
}