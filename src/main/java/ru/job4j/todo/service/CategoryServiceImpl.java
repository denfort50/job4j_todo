package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.repository.CategoryRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category findById(int id) {
        return categoryRepository.findById(id);
    }

    @Override
    public List<Category> getCategories(HttpServletRequest httpServletRequest) {
        List<String> categoriesNumbers = Arrays.stream(httpServletRequest.getParameterValues("category.id")).toList();
        return categoriesNumbers.stream()
                .map(category -> categoryRepository.findById(Integer.parseInt(category))).toList();
    }
}
