package com.example.restapp.repository;

import com.example.restapp.exceptions.NotFoundException;
import com.example.restapp.model.College;
import com.example.restapp.model.Student;
import com.example.restapp.model.Subject;
import com.example.restapp.utils.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CollegeDAO {
    private static final String QUERY_CREATE_COLLEGE = "INSERT INTO colleges (college_name, rating) VALUES (?, ?)";
    private static final String QUERY_READ_COLLEGE_BY_NAME = "SELECT * FROM colleges WHERE college_name = ?";
    private static final String QUERY_READ_ALL_COLLEGES = "SELECT * FROM colleges";
    private static final String QUERY_UPDATE_COLLEGE_RATING = "UPDATE colleges SET rating = ? WHERE college_name = ?";
    private static final String QUERY_UPDATE_NAME_COLLEGE = "UPDATE colleges SET college_name = ? WHERE college_name = ?";
    private static final String QUERY_DELETE_COLLEGE = "DELETE FROM colleges WHERE college_name = ?";
    private static final String QUERY_GET_LIST_STUDENTS = "SELECT s.student_name, s.college_id, s.gpa " +
            "FROM colleges c " +
            "JOIN students s ON c.college_id = s.college_id " +
            "WHERE c.college_name = ? ";
    private static final String QUERY_GET_LIST_SUBJECTS = "SELECT s.subject_name " +
            "FROM colleges c " +
            "JOIN college_subject сs ON c.college_id = сs.college_id " +
            "JOIN subjects s ON s.subject_id = сs.subject_id " +
            "WHERE c.college_name = ? ";

    public boolean createCollege(College college) {
        try (PreparedStatement pst = DatabaseUtil.getConnection().prepareStatement(QUERY_CREATE_COLLEGE)) {
            pst.setString(1, college.getNameCollege());
            pst.setDouble(2, college.getRating());
            pst.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<College> selectAllColleges() {
        List<College> colleges = new ArrayList<>();

        try (PreparedStatement pst = DatabaseUtil.getConnection().prepareStatement(QUERY_READ_ALL_COLLEGES)) {

            try (ResultSet resultSet = pst.executeQuery()) {
                while (resultSet.next()) {
                    double rating = resultSet.getDouble("rating");
                    String name = resultSet.getString("college_name");
                    List<Student> listStudent = fetchStudentsFromDatabase(name);
                    List<Subject> listSubject = fetchSubjectsFromDatabase(name);
                    College college = new College(rating, name);

                    college.setSubjectList(listSubject);
                    college.setStudentList(listStudent);
                    colleges.add(college);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return colleges;
    }

    public College selectCollegeByName(String nameCollege) {
        College college = null;
        List<Student> listStudent = fetchStudentsFromDatabase(nameCollege);
        List<Subject> listSubject = fetchSubjectsFromDatabase(nameCollege);

        try (PreparedStatement pst = DatabaseUtil.getConnection().prepareStatement(QUERY_READ_COLLEGE_BY_NAME)) {
            pst.setString(1, nameCollege);

            try (ResultSet resultSet = pst.executeQuery()) {
                if (resultSet.next()) {

                    double rating = resultSet.getDouble("rating");
                    String collegeName = resultSet.getString("college_name");
                    college = new College(rating, collegeName);

                    college.setStudentList(listStudent);
                    college.setSubjectList(listSubject);
                }
            }
        } catch (SQLException e) {
            throw new NotFoundException("Error: college this name " + nameCollege + "is does not exist");
        }
        return college;
    }

    public boolean updateCollegeName(String oldName, String newName) {
        try (PreparedStatement pst = DatabaseUtil.getConnection().prepareStatement(QUERY_UPDATE_NAME_COLLEGE)) {
            pst.setString(1, newName);
            pst.setString(2, oldName);
            pst.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateCollegeRating(String nameCollege, double rating) {
        try (PreparedStatement pst = DatabaseUtil.getConnection().prepareStatement(QUERY_UPDATE_COLLEGE_RATING)) {
            pst.setDouble(1, rating);
            pst.setString(2, nameCollege);
            pst.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteCollege(String name) {
        try (PreparedStatement pst = DatabaseUtil.getConnection().prepareStatement(QUERY_DELETE_COLLEGE)) {

            pst.setString(1, name);
            pst.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Этот метод извлекает список студентов из базы данных по заданному имени колледжа.
     *
     * @param collegeName имя колледжа, для которого требуется получить список студентов
     * @return список студентов, учащихся в заданном колледже
     */
    private List<Student> fetchStudentsFromDatabase(String collegeName) {
        List<Student> studentList = new ArrayList<>();

        try (PreparedStatement pst = DatabaseUtil.getConnection().prepareStatement(QUERY_GET_LIST_STUDENTS)) {

            pst.setString(1, collegeName);

            try (ResultSet resultSet = pst.executeQuery()) {
                while (resultSet.next()) {

                    String studentName = resultSet.getString("student_name");
                    int collegetId = resultSet.getInt("college_id");
                    double studentGpa = resultSet.getDouble("gpa");
                    Student student = new Student(studentName, collegetId, studentGpa);

                    studentList.add(student);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return studentList;
    }

    /**
     * Этот метод извлекает список предметов из базы данных для указанного колледжа.
     *
     * @param collegeName имя колледжа, для которого требуется получить список предметов
     * @return список предметов, преподаваемых в указанном колледже
     */
    private List<Subject> fetchSubjectsFromDatabase(String collegeName) {
        List<Subject> subjectList = new ArrayList<>();
        try (PreparedStatement pst = DatabaseUtil.getConnection().prepareStatement(QUERY_GET_LIST_SUBJECTS)) {

            pst.setString(1, collegeName);

            try (ResultSet resultSet = pst.executeQuery()) {
                while (resultSet.next()) {

                    String subjectName = resultSet.getString("subject_name");
                    Subject subject = new Subject(subjectName);

                    subjectList.add(subject);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return subjectList;
    }
}
