package com.example.restapp.mappers;

import com.example.restapp.dto.StudentDTO;
import com.example.restapp.model.Student;

public class StudentMapper {
    public static StudentDTO toStudentDto(Student student) {
        StudentDTO studentDTO = new StudentDTO();

        studentDTO.setStudentNameDto(student.getStudentName());
        studentDTO.setCollegeId(student.getCollegeId());
        studentDTO.setGpa(student.getGpa());
        return studentDTO;
    }

    public static Student toStudentModel(StudentDTO studentDTO) {
        Student student = new Student();

        student.setStudentName(studentDTO.getStudentNameDto());
        student.setCollegeId(studentDTO.getCollegeId());
        student.setGpa(studentDTO.getGpa());
        return student;
    }
}
