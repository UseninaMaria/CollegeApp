package com.example.restapp.service;


import com.example.restapp.dto.StudentDTO;
import com.example.restapp.model.Student;
import com.example.restapp.repository.StudentDAO;

import static com.example.restapp.mappers.StudentMapper.toStudentDto;
import static com.example.restapp.mappers.StudentMapper.toStudentModel;

public class StudentService {
    private static StudentService studentInstance;
    private static final StudentDAO STUDENT_DAO = new StudentDAO();

    private StudentService() {
    }

    public static StudentService getInstanceStudent() {
        if (studentInstance == null) {
            return new StudentService();
        } else return studentInstance;
    }

    public boolean createStudent(StudentDTO studentDTO) {
        Student student = toStudentModel(studentDTO);
        if (!(STUDENT_DAO.selectStudentByName(student.getStudentName()))) {
            STUDENT_DAO.createStudent(student);
            return true;
        }
        return false;
    }

    public StudentDTO readStudent(long studentId) {
        return STUDENT_DAO.selectStudent(studentId);
    }

    public boolean updateStudent(StudentDTO studentDTO, String oldName) {
        Student student = toStudentModel(studentDTO);

        if ((STUDENT_DAO.selectStudentByName(oldName))) {
            STUDENT_DAO.updateStudent(student, oldName);
            return true;
        }
        STUDENT_DAO.createStudent(student);
        return false;
    }

    public boolean deleteStudent(String studentName) {
        if ((STUDENT_DAO.selectStudentByName(studentName))) {
            return STUDENT_DAO.deleteStudent(studentName);
        }
        return false;
    }
}
