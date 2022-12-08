package ru.job4j.todo.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.model.Priority;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;
import ru.job4j.todo.service.CategoryService;
import ru.job4j.todo.service.PriorityService;
import ru.job4j.todo.service.TaskService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;
import java.util.stream.Collectors;

import static ru.job4j.todo.util.UserAttributeTool.addAttributeUser;
import static ru.job4j.todo.util.UserAttributeTool.getAttributeUser;

/**
 * Класс представляет собой контроллер для взаимодействия хранилища задач с представлениями
 * @author Denis Kalchenko
 * @version 1.0
 */
@Controller
@AllArgsConstructor
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;
    private final PriorityService priorityService;
    private final CategoryService categoryService;

    /**
     * Метод обрабатывает GET-запрос на получение списка всех задач
     * @param model модель в представлении
     * @return возвращает представление со списком всех задач
     */
    @GetMapping("")
    public String getAllTasks(Model model, HttpSession session) {
        List<Task> taskList = choseWayOfShowingDateOfCreated(taskService.findAll(), session);
        model.addAttribute("allTasks", taskList);
        model.addAttribute("tasksAreAbsent", taskService.findAll().isEmpty());
        addAttributeUser(model, session);
        return "allTasks";
    }

    /**
     * Метод обрабатывает GET-запрос на получение списка новых задач
     * @param model модель в представлении
     * @return возвращает представление со списком новых задач
     */
    @GetMapping("/undone")
    public String getNewTasks(Model model, HttpSession session) {
        List<Task> taskList = choseWayOfShowingDateOfCreated(taskService.findTasksByStatus(false), session);
        model.addAttribute("newTasks", taskList);
        addAttributeUser(model, session);
        return "newTasks";
    }

    /**
     * Метод обрабатывает GET-запрос на получение списка выполненных задач
     * @param model модель в представлении
     * @return возвращает представление со списком выполненных задач
     */
    @GetMapping("/done")
    public String getCompletedTasks(Model model, HttpSession session) {
        List<Task> taskList = choseWayOfShowingDateOfCreated(taskService.findTasksByStatus(true), session);
        model.addAttribute("completedTasks", taskList);
        addAttributeUser(model, session);
        return "completedTasks";
    }

    /**
     * Метод обрабатывает GET-запрос на получение представления с функционалом добавления задачи
     * @param model модель в представлении
     * @return возвращает представление, в котором можно добавить задачу
     */
    @GetMapping("/formAddTask")
    public String addTask(Model model) {
        model.addAttribute("task", new Task(0, "Название", "Описание", new Priority()));
        model.addAttribute("priorities", priorityService.findAll());
        model.addAttribute("categories", categoryService.findAll());
        return "addTask";
    }

    /**
     * Метод обрабатывает POST-запрос на создание задачи
     * @param task задача
     * @return возвращает представление со списком всех задач
     */
    @PostMapping("/createTask")
    public String createTask(@ModelAttribute Task task, HttpSession session, HttpServletRequest httpServletRequest) {
        task.setPriority(priorityService.findById(task.getPriority().getId()));
        task.setUser(getAttributeUser(session));
        List<Category> categories = categoryService.getCategories(httpServletRequest);
        task.setCategories(categories);
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
    public String getCompleteDescription(Model model, HttpSession session, @PathVariable("id") int id) {
        model.addAttribute("task", taskService.findById(id));
        addAttributeUser(model, session);
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
        model.addAttribute("priorities", priorityService.findAll());
        return "modifyTask";
    }

    /**
     * Метод обрабатывает POST-запрос на обновление задачи
     * @param task задача
     * @return возвращает представление со списком всех задач
     */
    @PostMapping("/updateTask")
    public String updateTask(@ModelAttribute Task task) {
        task.setPriority(priorityService.findById(task.getPriority().getId()));
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

    private List<Task> choseWayOfShowingDateOfCreated(List<Task> tasks, HttpSession session) {
        User user = getAttributeUser(session);
        Optional<TimeZone> timeZone = Optional.ofNullable(user.getTimezone());
        ZoneId zoneId;
        if (timeZone.isPresent()) {
            zoneId = timeZone.get().toZoneId();
        } else {
            zoneId = TimeZone.getDefault().toZoneId();
        }
        return tasks.stream().peek(task -> {
            ZonedDateTime before = task.getCreated();
            ZonedDateTime after = before.withZoneSameInstant(zoneId);
            task.setCreated(after);
        }).collect(Collectors.toList());
    }
}
