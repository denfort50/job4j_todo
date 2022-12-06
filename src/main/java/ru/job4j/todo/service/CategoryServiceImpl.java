package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.repository.CategoryRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
        String[] categories = httpServletRequest.getParameterValues("category.id");
        List<Integer> categoriesNumbers = Arrays.stream(categories).map(Integer::parseInt).collect(Collectors.toList());
        return categoryRepository.findCategoriesByIds(categoriesNumbers);
    }
}
