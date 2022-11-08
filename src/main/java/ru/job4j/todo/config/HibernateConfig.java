package ru.job4j.todo.config;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Класс представляет собой конфигурацию ORM Hibernate
 * @author Denis Kalchenko
 * @version 1.0
 */
@Configuration
public class HibernateConfig {

    /**
     * Метод создает объект SessionFactory для многократного использования в приложении
     * @return возвращает объект SessionFactory
     */
    @Bean(destroyMethod = "close")
    public SessionFactory getSessionFactory() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        return new MetadataSources(registry).buildMetadata().buildSessionFactory();
    }
}
