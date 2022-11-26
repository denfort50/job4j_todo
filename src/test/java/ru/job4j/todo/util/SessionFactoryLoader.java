package ru.job4j.todo.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public final class SessionFactoryLoader {

    private SessionFactoryLoader() {
        throw new UnsupportedOperationException("This is utility class and it cannot be instantiated");
    }

    public static StandardServiceRegistry getStandardServiceRegistry() {
        return new StandardServiceRegistryBuilder().configure().build();
    }

    public static SessionFactory getSessionFactory() {
        return new MetadataSources(getStandardServiceRegistry()).buildMetadata().buildSessionFactory();
    }
}
