package com.example.restapp.mappers;

import com.example.restapp.dto.StudentDTO;
import com.example.restapp.model.Student;
import org.junit.jupiter.api.Test;

import static com.example.restapp.mappers.StudentMapper.toStudentDto;
import static com.example.restapp.mappers.StudentMapper.toStudentModel;
import static org.junit.jupiter.api.Assertions.assertEquals;

 class StudentMapperTest {
    private final String TEST_NAME_STUDENT = "Test Student";
    @Test
    void testToStudentDto() {

        Student student = new Student(TEST_NAME_STUDENT, 123, 3.5);

        StudentDTO studentDTO = toStudentDto(student);

        assertEquals(TEST_NAME_STUDENT, studentDTO.getStudentNameDto());
        assertEquals(123, studentDTO.getCollegeId());
        assertEquals(3.5, studentDTO.getGpa());
    }

    @Test
    void testToStudentModel() {

        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setStudentNameDto(TEST_NAME_STUDENT);
        studentDTO.setGpa(8.0);
        studentDTO.setCollegeId(4);

        Student student = toStudentModel(studentDTO);

        assertEquals(TEST_NAME_STUDENT, student.getStudentName());
        assertEquals(4, student.getCollegeId());
        assertEquals(8.0, student.getGpa());
    }
}
