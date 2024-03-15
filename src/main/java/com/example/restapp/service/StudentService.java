package com.example.restapp.service;


import com.example.restapp.dto.StudentDTO;
import com.example.restapp.model.Student;
import com.example.restapp.repository.StudentDAO;

import static com.example.restapp.mappers.StudentMapper.toStudentDto;
import static com.example.restapp.mappers.StudentMapper.toStudentModel;

public class StudentService {
    private static StudentService studentInstance;
    private StudentDAO studentDAO;

    private StudentService() {
        studentDAO = new StudentDAO();
    }

    public StudentService(StudentDAO studentDAO) {
        this.studentDAO = studentDAO;
    }

    public static StudentService getInstanceStudent() {
        if (studentInstance == null) {
            return new StudentService();
        } else return studentInstance;
    }

    public boolean createStudent(StudentDTO studentDTO) {
        Student student = toStudentModel(studentDTO);
        if (!(studentDAO.selectStudentByName(student.getStudentName()))) {
            studentDAO.createStudent(student);
            return true;
        }
        return false;
    }

    public StudentDTO getStudentByName(String studentName) {
        if (!(studentDAO.selectStudent(studentName) == null)) {
            return toStudentDto(studentDAO.selectStudent(studentName));
        }
        return null;
    }

    public boolean updateStudent(StudentDTO studentDTO, String oldName) {
        Student student = toStudentModel(studentDTO);

        if ((studentDAO.selectStudentByName(oldName))) {
            studentDAO.updateStudent(student, oldName);
            return true;
        }
        studentDAO.createStudent(student);
        return false;
    }

    public boolean deleteStudent(String studentName) {
        if ((studentDAO.selectStudentByName(studentName))) {
            return studentDAO.deleteStudent(studentName);
        }
        return false;
    }
}
