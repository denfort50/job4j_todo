package ru.job4j.todo.service;

import ru.job4j.todo.model.Category;
import ru.job4j.todo.model.Task;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface CategoryService {

    List<Category> findAll();

    Category findById(int id);

    List<Category> getCategories(HttpServletRequest httpServletRequest);
}
