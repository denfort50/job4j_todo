package ru.job4j.todo.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс описывает модель данных Task – задача
 * @author Denis Kalchenko
 * @version 1.0
 */
@Entity
@Table(name = "tasks")
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
public class Task {

    /** Идентификатор задачи */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NonNull
    @EqualsAndHashCode.Include
    private int id;

    /** Название задачи */
    @NonNull
    @EqualsAndHashCode.Include
    private String title;

    /** Описание задачи */
    @NonNull
    @EqualsAndHashCode.Include
    private String description;

    /** Дата создания задачи */
    @EqualsAndHashCode.Include
    private LocalDateTime created = LocalDateTime.now();

    /** Флаг выполнения задачи */
    private boolean done = false;

    /** Принадлежность задачи к пользователю */

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    @EqualsAndHashCode.Include
    private User user;

    /** Приоритет задачи */
    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "priority_id")
    @EqualsAndHashCode.Include
    private Priority priority;

    /** Категории задачи */
    @NonNull
    @ManyToMany
    @JoinTable(
            name = "tasks_categories",
            joinColumns = {
                    @JoinColumn(name = "task_id") },
            inverseJoinColumns = {
                    @JoinColumn(name = "category_id") }
    )
    private List<Category> categories = new ArrayList<>();
}
