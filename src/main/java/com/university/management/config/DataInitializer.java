package com.university.management.config;

import com.university.management.entity.Student;
import com.university.management.repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Seeds initial student data on application startup if the database is empty.
 */
@Component
public class DataInitializer implements CommandLineRunner {

    private final StudentRepository repository;

    public DataInitializer(StudentRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) {
        if (repository.count() == 0) {
            repository.save(new Student("2207028", "Mehedi", "mehedi@gmail.com", "Computer Science"));
            repository.save(new Student("2207029", "Sulaiman", "sulaiman@gmail.com", "EEE"));
            repository.save(new Student("2207030", "Rupam", "rupam@gmail.com", "CSE"));
        }
    }
}
