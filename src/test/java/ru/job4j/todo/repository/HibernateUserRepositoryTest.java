package ru.job4j.todo.repository;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.job4j.todo.model.User;

import static org.assertj.core.api.Assertions.*;
import static ru.job4j.todo.util.SessionFactoryLoader.getSessionFactory;

class HibernateUserRepositoryTest {

    private final SessionFactory sessionFactory = getSessionFactory();

    private final CrudRepository crudRepository = new CrudRepositoryImpl(sessionFactory);

    private final UserRepository userRepository = new HibernateUserRepository(crudRepository);

    private final TaskRepository taskRepository = new HibernateTaskRepository(crudRepository);

    @BeforeEach
    void cleanTasksTable() {
        taskRepository.deleteAll();
    }

    @AfterEach
    void cleanTable() {
        userRepository.deleteAll();
    }

    @Test
    void whenAddThenSuccess() {
        User user = new User("Denis", "denfort", "password1");
        user = userRepository.add(user).orElseThrow();
        User userInDB = userRepository.findUserById(user.getId()).orElseThrow();
        assertThat(userInDB.getUsername()).isEqualTo(user.getUsername());
    }

    @Test
    void whenFindUserByLoginAndPasswordThenSuccess() {
        User user = new User("Andrey", "jacovich", "password2");
        user = userRepository.add(user).orElseThrow();
        User userInDB = userRepository.findUserByLoginAndPassword(user.getLogin(), user.getPassword()).orElseThrow();
        assertThat(userInDB.getUsername()).isEqualTo(user.getUsername());
    }

    @Test
    void whenDeleteAllThenSuccess() {
        User user1 = new User("Alex", "cryptoman", "password3");
        User user2 = new User("Jason", "professional", "password4");
        User user3 = new User("Arnold", "terminator", "password5");
        int user1Id = userRepository.add(user1).orElseThrow().getId();
        int user2Id = userRepository.add(user2).orElseThrow().getId();
        int user3Id = userRepository.add(user3).orElseThrow().getId();
        userRepository.deleteAll();
        assertThat(userRepository.findUserById(user1Id)).isEmpty();
        assertThat(userRepository.findUserById(user2Id)).isEmpty();
        assertThat(userRepository.findUserById(user3Id)).isEmpty();
    }
}