package ru.job4j.todo.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import ru.job4j.todo.model.User;

import static org.assertj.core.api.Assertions.*;

class HibernateUserRepositoryTest {

    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();

    private final SessionFactory sessionFactory = new MetadataSources(registry)
            .buildMetadata()
            .buildSessionFactory();

    private final UserRepository userRepository = new HibernateUserRepository(sessionFactory);

    @AfterEach
    void cleanTable() {
        userRepository.deleteAll();
    }

    @Test
    void whenAddThenSuccess() {
        User user = new User(1, "Denis", "mr_bond", "password");
        userRepository.add(user).orElseThrow();
        User userInDB = userRepository.findUserById(user.getId()).orElseThrow();
        assertThat(userInDB.getUsername()).isEqualTo(user.getUsername());
    }

    @Test
    void whenFindUserByLoginAndPasswordThenSuccess() {
        User user = new User(1, "Denis", "mr_bond", "password");
        userRepository.add(user).orElseThrow();
        User userInDB = userRepository.findUserByLoginAndPassword(user.getLogin(), user.getPassword()).orElseThrow();
        assertThat(userInDB.getUsername()).isEqualTo(user.getUsername());
    }

    @Test
    void whenDeleteAllThenSuccess() {
        User user1 = new User(1, "Denis", "mr_bond", "password");
        User user2 = new User(2, "Alex", "mr_crust", "password");
        User user3 = new User(3, "John", "mr_wick", "password");
        userRepository.add(user1);
        userRepository.add(user2);
        userRepository.add(user3);
        userRepository.deleteAll();
        assertThat(userRepository.findUserById(user1.getId())).isEmpty();
        assertThat(userRepository.findUserById(user2.getId())).isEmpty();
        assertThat(userRepository.findUserById(user3.getId())).isEmpty();

    }
}