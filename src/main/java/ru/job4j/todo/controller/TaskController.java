package ru.job4j.todo.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.service.TaskService;

/**
 * Класс представляет собой контроллер для взаимодействия хранилища задач с представлениями
 * @author Denis Kalchenko
 * @version 1.0
 */
@Controller
@AllArgsConstructor
@RequestMapping("/tasks")
public class TaskController {

    /** Взаимодействие с базой данных происходит через слой сервисов TaskService */
    private final TaskService taskService;

    /**
     * Метод обрабатывает GET-запрос на получение списка всех задач
     * @param model модель в представлении
     * @return возвращает представление со списком всех задач
     */
    @GetMapping("")
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
    @GetMapping("/undone")
    public String getNewTasks(Model model) {
        model.addAttribute("newTasks", taskService.findTasksByStatus(false));
        return "newTasks";
    }

    /**
     * Метод обрабатывает GET-запрос на получение списка выполненных задач
     * @param model модель в представлении
     * @return возвращает представление со списком выполненных задач
     */
    @GetMapping("/done")
    public String getCompletedTasks(Model model) {
        model.addAttribute("completedTasks", taskService.findTasksByStatus(true));
        return "completedTasks";
    }

    /**
     * Метод обрабатывает GET-запрос на получение представления с функционалом добавления задачи
     * @param model модель в представлении
     * @return возвращает представление, в котором можно добавить задачу
     */
    @GetMapping("/formAddTask")
    public String addTask(Model model) {
        model.addAttribute("task", new Task(0, "Название", "Описание"));
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
        return "redirect:/tasks";
    }

    /**
     * Метод обрабатывает GET-запрос на получение подробной информации о задаче
     * @param model модель в представлении
     * @param id идентификатор задачи
     * @return возвращает представление с подробной информацией о задаче
     */
    @GetMapping("/complete/{id}")
    public String getCompleteDescription(Model model, @PathVariable("id") int id) {
        model.addAttribute("task", taskService.findById(id));
        return "taskDescription";
    }

    /**
     * Метод обрабатывает POST-запрос на завершение выбранной задачи
     * @param task задача
     * @return возвращает представление со списком всех задач
     */
    @PostMapping("/completeTask")
    public String completeTask(@ModelAttribute Task task) {
        boolean result = taskService.complete(task.getId());
        if (!result) {
            return "redirect:/tasks/fail";
        }
        return "redirect:/tasks";
    }

    /**
     * Метод обрабатывает GET-запрос на получение представления с функционалом редактирования задачи
     * @param model модель в представлении
     * @param id идентификатор задачи
     * @return возвращает представление, в котором можно редактировать задачу
     */
    @GetMapping("/formModifyTask/{id}")
    public String modifyTask(Model model, @PathVariable("id") int id) {
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
        boolean result = taskService.update(task);
        if (!result) {
            return "redirect:/tasks/fail";
        }
        return "redirect:/tasks";
    }

    /**
     * Метод обрабатывает POST-запрос на удаление задачи
     * @param task задача
     * @return возвращает представление со списком всех задач
     */
    @PostMapping("/deleteTask")
    public String deleteTask(Model model, @ModelAttribute Task task) {
        boolean result = taskService.delete(task.getId());
        if (!result) {
            model.addAttribute("message", "Операцию выполнить не удалось");
            return "redirect:/tasks/fail";
        }
        return "redirect:/tasks";
    }

    /**
     * Метод обрабатывает POST-запрос на удаление всех задач
     * @return возвращает представление с пустым списком задач
     */
    @PostMapping("/deleteAllTasks")
    public String deleteAllTasks() {
        taskService.deleteAll();
        return "redirect:/tasks";
    }

    /**
     * Метод обрабатывает GET-запрос на получение представления с информацией об ошибке
     * @return возвращает представление с сообщением об ошибке
     */
    @GetMapping("/fail")
    public String fail() {
        return "fail";
    }

}
