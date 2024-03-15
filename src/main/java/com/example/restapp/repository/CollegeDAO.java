package com.example.restapp.repository;

import com.example.restapp.exceptions.NotFoundException;
import com.example.restapp.model.College;
import com.example.restapp.model.Student;
import com.example.restapp.model.Subject;
import com.example.restapp.utils.DatabaseConnection;
import com.example.restapp.utils.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CollegeDAO {
    DatabaseConnection databaseUtil;

    public CollegeDAO() {
        this.databaseUtil = new DatabaseConnection(DatabaseUtil.getDbDriver(),
                DatabaseUtil.getDbUrl(), DatabaseUtil.getDbUsername(),
                DatabaseUtil.getDbPassword());
    }

    public CollegeDAO(DatabaseConnection dbConnector) {
        this.databaseUtil = dbConnector;
    }

    private static final String QUERY_CREATE_COLLEGE = "INSERT INTO colleges (college_name, rating) VALUES (?, ?)";
    private static final String QUERY_READ_COLLEGE_BY_NAME = "SELECT s.student_name, s.college_id, s.gpa, c.college_name, c.rating " +
            "FROM colleges c " +
            "JOIN students s ON c.college_id = s.college_id " +
            "WHERE c.college_name = ? ";
    private static final String QUERY_READ_ALL_COLLEGES = "SELECT * FROM colleges";
    private static final String QUERY_UPDATE_COLLEGE_RATING = "UPDATE colleges SET rating = ? WHERE college_name = ?";
    private static final String QUERY_UPDATE_NAME_COLLEGE = "UPDATE colleges SET college_name = ? WHERE college_name = ?";
    private static final String QUERY_DELETE_COLLEGE = "DELETE FROM colleges WHERE college_name = ?";
    private static final String QUERY_GET_LIST_SUBJECTS = "SELECT s.subject_name " +
            "FROM colleges c " +
            "JOIN college_subject сs ON c.college_id = сs.college_id " +
            "JOIN subjects s ON s.subject_id = сs.subject_id " +
            "WHERE c.college_name = ? ";
    private static final String QUERY_UPDATE_COLLEGE_LIST_SUB = "INSERT INTO college_subject (college_id, subject_id) " +
            "SELECT c.college_id, s.subject_id " +
            "FROM colleges c, subjects s " +
            "WHERE c.college_name = ? AND s.subject_name = ? ";

    public boolean createCollege(College college) {
        try (Connection connection = databaseUtil.getConnection();
             PreparedStatement pst = connection.prepareStatement(QUERY_CREATE_COLLEGE)) {
            pst.setString(1, college.getNameCollege());
            pst.setDouble(2, college.getRating());
            pst.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new NotFoundException();
        }
    }

    public List<College> selectAllColleges() {
        List<College> colleges = new ArrayList<>();

        try (PreparedStatement pst = databaseUtil.getConnection().prepareStatement(QUERY_READ_ALL_COLLEGES)) {

            try (ResultSet resultSet = pst.executeQuery()) {
                while (resultSet.next()) {
                    double rating = resultSet.getDouble("rating");
                    String name = resultSet.getString("college_name");
                    College college = new College(rating, name);

                    colleges.add(college);
                }
            }
        } catch (SQLException e) {
            throw new NotFoundException();
        }
        return colleges;
    }

    public College selectCollegeByName(String nameCollege) {
        College college = null;
        List<Student> listStudent = new ArrayList<>();
        List<Subject> listSubject = new ArrayList<>();

        try (PreparedStatement pst = databaseUtil.getConnection().prepareStatement(QUERY_READ_COLLEGE_BY_NAME)) {
            pst.setString(1, nameCollege);

            try (ResultSet resultSet = pst.executeQuery()) {
                while (resultSet.next()) {

                    double rating = resultSet.getDouble("rating");
                    String collegeName = resultSet.getString("college_name");
                    college = new College(rating, collegeName);

                    int collegeId = resultSet.getInt("college_id");
                    double gpa = resultSet.getDouble("gpa");
                    String studentName = resultSet.getString("student_name");

                    listStudent.add(new Student(studentName, collegeId, gpa));
                }
                college.setStudentList(listStudent);
            }
        } catch (SQLException e) {
            throw new NotFoundException("Error: college this name " + nameCollege + "is does not exist");
        }

        try (PreparedStatement pst = databaseUtil.getConnection().prepareStatement(QUERY_GET_LIST_SUBJECTS)) {
            pst.setString(1, nameCollege);

            try (ResultSet resultSet = pst.executeQuery()) {
                while (resultSet.next()) {
                    String subjectName = resultSet.getString("subject_name");
                    listSubject.add(new Subject(subjectName));
                    college.setSubjectList(listSubject);
                }
            }
        } catch (SQLException e) {
            throw new NotFoundException("Error: college this name " + nameCollege + "is does not exist");
        }
        return college;
    }

    public boolean updateCollegeName(String oldName, String newName) {
        try (PreparedStatement pst = databaseUtil.getConnection().prepareStatement(QUERY_UPDATE_NAME_COLLEGE)) {
            pst.setString(1, newName);
            pst.setString(2, oldName);
            pst.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new NotFoundException("Error: college this name " + oldName + "is does not exist");
        }
    }

    public boolean updateCollegeRating(String nameCollege, double rating) {
        try (PreparedStatement pst = databaseUtil.getConnection().prepareStatement(QUERY_UPDATE_COLLEGE_RATING)) {
            pst.setDouble(1, rating);
            pst.setString(2, nameCollege);
            pst.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new NotFoundException("Error: college this name " + nameCollege + "is does not exist");
        }
    }

    public boolean addCollegeListSubject(String nameCollege, String subject) {
        try (PreparedStatement pst = databaseUtil.getConnection().prepareStatement(QUERY_UPDATE_COLLEGE_LIST_SUB)) {
            pst.setString(1, nameCollege);
            pst.setString(2, subject);
            pst.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new NotFoundException("Duplicate key value or subject or colleges does not exist");
        }
    }

    public boolean deleteCollege(String name) {
        try (PreparedStatement pst = databaseUtil.getConnection().prepareStatement(QUERY_DELETE_COLLEGE)) {
            pst.setString(1, name);
            pst.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new NotFoundException("Error: college this name " + name + "is does not exist");
        }
    }
}
