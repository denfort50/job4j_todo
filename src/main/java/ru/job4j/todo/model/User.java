package ru.job4j.todo.model;

import lombok.*;

import javax.persistence.*;

/**
 * Класс описывает модель данных User – пользователь
 * @author Denis Kalchenko
 * @version 1.0
 */
@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NonNull
    private String username;

    @NonNull
    private String login;

    @NonNull
    private String password;
}
