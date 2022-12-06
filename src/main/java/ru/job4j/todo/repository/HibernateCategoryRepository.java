package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Category;

import java.util.List;
import java.util.Map;

@Repository
@AllArgsConstructor
public class HibernateCategoryRepository implements CategoryRepository {

    private static final String SELECT_ALL_CATEGORIES = "FROM Category";
    private static final String SELECT_CATEGORY_BY_ID = "FROM Category c WHERE c.id = :fId";
    private static final String SELECT_CATEGORIES_BY_IDS = "FROM Category c WHERE c.id in (:fIds)";

    private CrudRepository crudRepository;

    @Override
    public List<Category> findAll() {
        return crudRepository.queryAndGetList(SELECT_ALL_CATEGORIES, Category.class);
    }

    @Override
    public Category findById(int id) {
        return (Category) crudRepository.queryAndGetObject(SELECT_CATEGORY_BY_ID, Category.class, Map.of("fId", id));
    }

    @Override
    public List<Category> findCategoriesByIds(List<Integer> ids) {
        return crudRepository.queryAndGetList(SELECT_CATEGORIES_BY_IDS, Category.class, "fIds", ids);
    }
}
