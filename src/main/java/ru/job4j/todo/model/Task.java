package ru.job4j.todo.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Класс описывает модель данных Task – задача
 * @author Denis Kalchenko
 * @version 1.0
 */
@Entity
@Table(name = "tasks")
@Data
public class Task {

    /** Идентификатор задачи */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /** Название задачи */
    private String title;

    /** Описание задачи */
    private String description;

    /** Дата создания задачи */
    private LocalDateTime created = LocalDateTime.now();

    /** Флаг выполнения задачи */
    private boolean done = false;

    /** Дефолтный конструктор */
    public Task() { }

    /** Конструктор класса инициализирует параметры
     * @param id идентификатор
     * @param title название
     * @param description описание
     * @param created дата создания
     */
    public Task(int id, String title, String description, LocalDateTime created) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.created = created;
    }
}
