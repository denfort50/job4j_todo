package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Priority;

import java.util.List;
import java.util.Map;

@Repository
@AllArgsConstructor
public class HibernatePriorityRepository implements PriorityRepository {

    private static final String SELECT_ALL_PRIORITIES = "FROM Priority";
    private static final String SELECT_PRIORITY_BY_ID = "FROM Priority p WHERE p.id = :fId";

    private CrudRepository crudRepository;

    @Override
    public List<Priority> findAll() {
        return crudRepository.queryAndGetList(SELECT_ALL_PRIORITIES, Priority.class);
    }

    @Override
    public Priority findById(int id) {
        return (Priority) crudRepository.queryAndGetObject(SELECT_PRIORITY_BY_ID, Priority.class, Map.of("fId", id));
    }
}
