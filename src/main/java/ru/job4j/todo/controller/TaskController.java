package ru.job4j.todo.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.service.TaskService;

import java.time.LocalDateTime;

/**
 * Класс представляет собой контроллер для взаимодействия хранилища задач с представлениями
 * @author Denis Kalchenko
 * @version 1.0
 */
@Controller
@AllArgsConstructor
public class TaskController {

    /** Взаимодействие с базой данных происходит через слой сервисов TaskService */
    private final TaskService taskService;

    /**
     * Метод обрабатывает GET-запрос на получение списка всех задач
     * @param model модель в представлении
     * @return возвращает представление со списком всех задач
     */
    @GetMapping("/formAllTasks")
    public String getAllTasks(Model model) {
        model.addAttribute("allTasks", taskService.findAll());
        model.addAttribute("tasksAreAbsent", taskService.findAll().isEmpty());
        return "allTasks";
    }

    /**
     * Метод обрабатывает GET-запрос на получение списка новых задач
     * @param model модель в представлении
     * @return возвращает представление со списком новых задач
     */
    @GetMapping("/formNewTasks")
    public String getNewTasks(Model model) {
        model.addAttribute("newTasks", taskService.findNew());
        return "newTasks";
    }

    /**
     * Метод обрабатывает GET-запрос на получение списка выполненных задач
     * @param model модель в представлении
     * @return возвращает представление со списком выполненных задач
     */
    @GetMapping("/formCompletedTasks")
    public String getCompletedTasks(Model model) {
        model.addAttribute("completedTasks", taskService.findCompleted());
        return "completedTasks";
    }

    /**
     * Метод обрабатывает GET-запрос на получение представления с функционалом добавления задачи
     * @param model модель в представлении
     * @return возвращает представление, в котором можно добавить задачу
     */
    @GetMapping("/formAddTask")
    public String addTask(Model model) {
        model.addAttribute("task", new Task(0, "Название", "Описание", LocalDateTime.now()));
        return "addTask";
    }

    /**
     * Метод обрабатывает POST-запрос на создание задачи
     * @param task задача
     * @return возвращает представление со списком всех задач
     */
    @PostMapping("/createTask")
    public String createTask(@ModelAttribute Task task) {
        taskService.add(task);
        return "redirect:/formAllTasks";
    }

    /**
     * Метод обрабатывает GET-запрос на получение подробной информации о задаче
     * @param model модель в представлении
     * @param id идентификатор задачи
     * @return возвращает представление с подробной информацией о задаче
     */
    @GetMapping("/taskDescription/{taskId}")
    public String getTaskDescription(Model model, @PathVariable("taskId") int id) {
        model.addAttribute("task", taskService.findById(id));
        return "taskDescription";
    }

    /**
     * Метод обрабатывает GET-запрос на завершение выбранной задачи
     * @param taskId идентификатор задачи
     * @return возвращает представление со списком всех задач
     */
    @GetMapping("/completeTask/{taskId}")
    public String completeTask(@PathVariable("taskId") int taskId) {
        taskService.complete(taskId);
        return "redirect:/formAllTasks";
    }

    /**
     * Метод обрабатывает GET-запрос на получение представления с функционалом редактирования задачи
     * @param model модель в представлении
     * @param id идентификатор задачи
     * @return возвращает представление, в котором можно редактировать задачу
     */
    @GetMapping("/formModifyTask/{taskId}")
    public String modifyTask(Model model, @PathVariable("taskId") int id) {
        model.addAttribute("task", taskService.findById(id));
        return "modifyTask";
    }

    /**
     * Метод обрабатывает POST-запрос на обновление задачи
     * @param task задача
     * @return возвращает представление со списком всех задач
     */
    @PostMapping("/updateTask")
    public String updateTask(@ModelAttribute Task task) {
        taskService.update(task);
        return "redirect:/formAllTasks";
    }

    /**
     * Метод обрабатывает GET-запрос на удаление задачи
     * @param taskId идентификатор задачи
     * @return возвращает представление со списком всех задач
     */
    @GetMapping("/deleteTask/{taskId}")
    public String deleteTask(@PathVariable("taskId") int taskId) {
        taskService.delete(taskId);
        return "redirect:/formAllTasks";
    }

    /**
     * Метод обрабатывает POST-запрос на удаление всех задач
     * @return возвращает представление с пустым списком задач
     */
    @PostMapping("/deleteAllTasks")
    public String deleteAllTasks() {
        taskService.deleteAll();
        return "redirect:/formAllTasks";
    }

}
