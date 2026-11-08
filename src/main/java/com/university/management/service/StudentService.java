package com.university.management.service;

import com.university.management.dto.StudentRequest;
import com.university.management.dto.StudentResponse;
import com.university.management.entity.Student;
import com.university.management.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<StudentResponse> getAllStudents() {
        return List.of();
    }

    public StudentResponse getStudentById(Long id) {
        return studentRepository.findById(id)
                .map(StudentResponse::fromEntity)
                .orElse(null);
    }

    public StudentResponse createStudent(StudentRequest request) {
        Student student = new Student(
                request.getRoll(),
                request.getName(),
                request.getEmail(),
                request.getDepartment()
        );
        return StudentResponse.fromEntity(studentRepository.save(student));
    }

    public StudentResponse updateStudent(Long id, StudentRequest request) {
        Student student = studentRepository.findById(id).orElse(null);
        if (student == null) {
            return null;
        }

        student.setRoll(request.getRoll());
        student.setName(request.getName());
        student.setEmail(request.getEmail());
        student.setDepartment(request.getDepartment());

        return StudentResponse.fromEntity(studentRepository.save(student));
    }

    public boolean deleteStudent(Long id) {
        if (!studentRepository.existsById(id)) {
            return false;
        }
        studentRepository.deleteById(id);
        return true;
    }
}
