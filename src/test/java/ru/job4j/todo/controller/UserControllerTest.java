package ru.job4j.todo.controller;

import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import ru.job4j.todo.model.User;
import ru.job4j.todo.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Test
    void whenLoginPageThenSuccess() {
        Model model = mock(Model.class);
        HttpSession session = mock(HttpSession.class);
        UserService userService = mock(UserService.class);
        UserController userController = new UserController(userService);
        String page = userController.loginPage(model, session, true);
        verify(model).addAttribute("fail", true);
        assertThat(page).isEqualTo("loginPage");
    }

    @Test
    void whenLoginThenFail() {
        User user = new User(1, "Denis", "mr_bond", "password");
        HttpSession session = mock(HttpSession.class);
        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
        UserService userService = mock(UserService.class);
        UserController userController = new UserController(userService);
        when(userService.findUserByLoginAndPassword(user.getLogin(), user.getPassword())).thenReturn(Optional.empty());
        when(httpServletRequest.getSession()).thenReturn(session);
        String page = userController.login(user, httpServletRequest);
        assertThat(page).isEqualTo("redirect:/users/loginPage?fail=true");
    }

    @Test
    void whenLoginThenSuccess() {
        User user = new User(1, "Denis", "mr_bond", "password");
        HttpSession session = mock(HttpSession.class);
        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
        UserService userService = mock(UserService.class);
        UserController userController = new UserController(userService);
        when(userService.findUserByLoginAndPassword(user.getLogin(), user.getPassword())).thenReturn(Optional.of(user));
        when(httpServletRequest.getSession()).thenReturn(session);
        String page = userController.login(user, httpServletRequest);
        assertThat(page).isEqualTo("redirect:/tasks");
    }

    @Test
    void whenLogoutThenSuccess() {
        HttpSession session = mock(HttpSession.class);
        UserService userService = mock(UserService.class);
        UserController userController = new UserController(userService);
        String page = userController.logout(session);
        assertThat(page).isEqualTo("redirect:/tasks");
    }

    @Test
    void whenAddUserThenSuccess() {
        User user = new User(0, "username", "login", "password");
        Model model = mock(Model.class);
        HttpSession session = mock(HttpSession.class);
        UserService userService = mock(UserService.class);
        UserController userController = new UserController(userService);
        String page = userController.addUser(model, session);
        verify(model).addAttribute("newUser", user);
        assertThat(page).isEqualTo("addUser");
    }

    @Test
    void whenRegistrationThenFail() {
        User user = new User(1, "Denis", "mr_bond", "password");
        UserService userService = mock(UserService.class);
        when(userService.add(user)).thenReturn(Optional.empty());
        UserController userController = new UserController(userService);
        String page = userController.registration(user);
        assertThat(page).isEqualTo("redirect:/users/fail");
    }

    @Test
    void whenRegistrationThenSuccess() {
        User user = new User(1, "Denis", "mr_bond", "password");
        UserService userService = mock(UserService.class);
        when(userService.add(user)).thenReturn(Optional.of(user));
        UserController userController = new UserController(userService);
        String page = userController.registration(user);
        assertThat(page).isEqualTo("redirect:/users/success");
    }

    @Test
    void whenFail() {
        Model model = mock(Model.class);
        HttpSession session = mock(HttpSession.class);
        UserService userService = mock(UserService.class);
        UserController userController = new UserController(userService);
        String page = userController.fail(model, session);
        assertThat(page).isEqualTo("registrationFail");
    }

    @Test
    void whenSuccess() {
        Model model = mock(Model.class);
        HttpSession session = mock(HttpSession.class);
        UserService userService = mock(UserService.class);
        UserController userController = new UserController(userService);
        String page = userController.success(model, session);
        assertThat(page).isEqualTo("registrationSuccess");
    }
}