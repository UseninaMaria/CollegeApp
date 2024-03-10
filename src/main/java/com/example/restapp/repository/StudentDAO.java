package com.example.restapp.repository;

import com.example.restapp.dto.StudentDTO;
import com.example.restapp.model.Student;
import com.example.restapp.utils.DatabaseUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.example.restapp.mappers.StudentMapper.toStudentDto;

public class StudentDAO {
    private static final String QUERY_CREATE_STUDENT = "INSERT INTO students (college_id, student_name, gpa) VALUES (?,?, ?)";
    private static final String QUERY_SELECT_ID_STUDENT ="SELECT s.student_name, c.college_id, s.gpa " +
            "FROM students s " +
            "INNER JOIN colleges c ON s.college_id = c.college_id " +
            "WHERE s.student_id = ?";
    private static final String QUERY_SELECT_NAME_STUDENT ="SELECT s.student_name " +
            "FROM students s " +
            "WHERE s.student_name = ?";
    private static final String QUERY_UPDATE_STUDENT = "UPDATE students SET college_id = ?, student_name = ?, gpa = ? WHERE student_name = ?";
    private static final String QUERY_DELETE_STUDENT = "DELETE FROM students WHERE student_name = ?";

    public void createStudent(Student student) {
        try (PreparedStatement pst =  DatabaseUtil.getConnection().prepareStatement(QUERY_CREATE_STUDENT)) {
            pst.setInt(1, student.getCollegeId());
            pst.setString(2, student.getStudentName());
            pst.setDouble(3, student.getGpa());
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            //Описать ошибки
        }
    }

    public StudentDTO selectStudent(long studentId) {
        try (PreparedStatement pst = DatabaseUtil.getConnection().prepareStatement(QUERY_SELECT_ID_STUDENT)) {
            pst.setLong(1, studentId);

            try (ResultSet resultSet = pst.executeQuery()) {
                if (resultSet.next()) {

                    String studentName = resultSet.getString("student_name");
                    int collegeId = resultSet.getInt("college_id");
                    double gpa = resultSet.getDouble("gpa");
                    Student student = new Student(studentName, collegeId, gpa);

                    return toStudentDto(student);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Вспомогательный метод для выбора студента по имени из базы данных. Помогает осуществить проверку
     * на уже существующего студента
     * @param name Имя студента для поиска
     * @return true, если студент с указанным именем существует в базе данных, в противном случае - false
     */
    public boolean selectStudentByName(String name) {
        try (PreparedStatement pst = DatabaseUtil.getConnection().prepareStatement(QUERY_SELECT_NAME_STUDENT)) {
            pst.setString(1, name);

            try (ResultSet resultSet = pst.executeQuery()) {
                    return resultSet.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Student updateStudent(Student student, String oldName) {
        try (PreparedStatement pst =  DatabaseUtil.getConnection().prepareStatement(QUERY_UPDATE_STUDENT)) {
            pst.setInt(1, student.getCollegeId());
            pst.setString(2, student.getStudentName());
            pst.setDouble(3, student.getGpa());
            pst.setString(4, oldName);
            pst.executeUpdate();

            return student;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean deleteStudent(String studentName) {
        try (PreparedStatement pst =  DatabaseUtil.getConnection().prepareStatement(QUERY_DELETE_STUDENT)) {
            pst.setString(1, studentName);
            pst.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
