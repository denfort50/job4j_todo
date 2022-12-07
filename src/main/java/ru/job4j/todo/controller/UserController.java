package ru.job4j.todo.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.User;
import ru.job4j.todo.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.ZoneId;
import java.util.Optional;
import java.util.TimeZone;

import static ru.job4j.todo.util.UserAttributeTool.addAttributeUser;

/**
 * Класс представляет собой контроллер для взаимодействия хранилища пользователей с представлениями
 * @author Denis Kalchenko
 * @version 1.0
 */
@Controller
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/loginPage")
    public String loginPage(Model model, HttpSession session,
                            @RequestParam(name = "fail", required = false) Boolean fail) {
        model.addAttribute("fail", fail != null);
        addAttributeUser(model, session);
        return "loginPage";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute User user, HttpServletRequest req) {
        Optional<User> userDB = userService.findUserByLoginAndPassword(user.getLogin(), user.getPassword());
        if (userDB.isEmpty()) {
            return "redirect:/users/loginPage?fail=true";
        }
        HttpSession session = req.getSession();
        session.setAttribute("user", userDB.get());
        return "redirect:/tasks";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/tasks";
    }

    @GetMapping("/add")
    public String addUser(Model model, HttpSession session) {
        model.addAttribute("newUser", new User(0, "username", "login", "password", TimeZone.getDefault()));
        model.addAttribute("timeZones", TimeZone.getAvailableIDs());
        addAttributeUser(model, session);
        return "addUser";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute User user, @RequestParam("timeZoneId") String timeZoneId) {
        user.setTimezone(TimeZone.getTimeZone(ZoneId.of(timeZoneId)));
        Optional<User> regUser = userService.add(user);
        if (regUser.isEmpty()) {
            return "redirect:/users/fail";
        }
        return "redirect:/users/success";
    }

    @GetMapping("/fail")
    public String fail(Model model, HttpSession session) {
        addAttributeUser(model, session);
        return "registrationFail";
    }

    @GetMapping("/success")
    public String success(Model model, HttpSession session) {
        addAttributeUser(model, session);
        return "registrationSuccess";
    }
}
