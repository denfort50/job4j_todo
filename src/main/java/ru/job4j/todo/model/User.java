package ru.job4j.todo.model;

import lombok.*;

import javax.persistence.*;
import java.util.TimeZone;

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

    @Column(name = "user_zone")
    private TimeZone timezone;
}
