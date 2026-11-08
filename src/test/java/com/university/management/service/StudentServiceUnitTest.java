package com.university.management.service;

import com.university.management.dto.StudentRequest;
import com.university.management.dto.StudentResponse;
import com.university.management.entity.Student;
import com.university.management.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class StudentServiceUnitTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    private Student student;
    private StudentRequest studentRequest;

    @BeforeEach
    void setUp() {
        student = new Student(1L, "2207028", "Md Mehedi", "mehedi@gmail.com", "Computer Science");
        studentRequest = new StudentRequest("2207028", "Md Mehedi", "mehedi@gmail.com", "Computer Science");
    }

    @Test
    @DisplayName("Unit Test 1: getAllStudents should return all students")
    void testGetAllStudents() {
        when(studentRepository.findAll()).thenReturn(List.of(student));

        List<StudentResponse> students = studentService.getAllStudents();

        assertThat(students).hasSize(1);
        assertThat(students.get(0).getRoll()).isEqualTo("2207028");
    }

    @Test
    @DisplayName("Unit Test 2: getStudentById should return student when found")
    void testGetStudentById_Found() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        StudentResponse result = studentService.getStudentById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Md Mehedi");
    }

    @Test
    @DisplayName("Unit Test 3: getStudentById should return null when not found")
    void testGetStudentById_NotFound() {
        when(studentRepository.findById(99L)).thenReturn(Optional.empty());

        StudentResponse result = studentService.getStudentById(99L);

        assertThat(result).isNull();
    }

    @Test
    @DisplayName("Unit Test 4: createStudent should save and return student")
    void testCreateStudent() {
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        StudentResponse result = studentService.createStudent(studentRequest);

        assertThat(result).isNotNull();
        assertThat(result.getRoll()).isEqualTo("2207028");
        verify(studentRepository, times(1)).save(any(Student.class));
    }

    @Test
    @DisplayName("Unit Test 5: updateStudent should update and return student when found")
    void testUpdateStudent_Found() {
        StudentRequest updateRequest = new StudentRequest("2207028", "Mehedi Updated", "mehedi.new@gmail.com", "Computer Science");
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(studentRepository.save(any(Student.class))).thenAnswer(invocation -> invocation.getArgument(0));

        StudentResponse result = studentService.updateStudent(1L, updateRequest);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Mehedi Updated");
        assertThat(result.getEmail()).isEqualTo("mehedi.new@gmail.com");
    }

    @Test
    @DisplayName("Unit Test 6: deleteStudent should return true when student is deleted")
    void testDeleteStudent_Success() {
        when(studentRepository.existsById(1L)).thenReturn(true);
        doNothing().when(studentRepository).deleteById(1L);

        boolean isDeleted = studentService.deleteStudent(1L);

        assertThat(isDeleted).isTrue();
        verify(studentRepository, times(1)).deleteById(1L);
    }
}

