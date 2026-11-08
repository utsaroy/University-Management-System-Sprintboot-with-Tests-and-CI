package com.university.management.dto;

import com.university.management.entity.Student;

/**
 * Response payload representing a student.
 */
public class StudentResponse {

    private Long id;
    private String roll;
    private String name;
    private String email;
    private String department;

    public StudentResponse() {
    }

    public StudentResponse(Long id, String roll, String name, String email, String department) {
        this.id = id;
        this.roll = roll;
        this.name = name;
        this.email = email;
        this.department = department;
    }

    public static StudentResponse fromEntity(Student student) {
        if (student == null) {
            return null;
        }
        return new StudentResponse(
                student.getId(),
                student.getRoll(),
                student.getName(),
                student.getEmail(),
                student.getDepartment()
        );
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoll() {
        return roll;
    }

    public void setRoll(String roll) {
        this.roll = roll;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
