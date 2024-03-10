package com.example.restapp.repository;

import com.example.restapp.dto.SubjectDTO;
import com.example.restapp.model.Subject;
import com.example.restapp.utils.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.example.restapp.mappers.SubjectMapper.toSubjectDto;

public class SubjectDAO {
    private static final String QUERY_CREATE_SUBJECTS = "INSERT INTO subjects (subject_name) VALUES (?)";
    private static final String QUERY_READ_SUBJECTS = "SELECT subject_name FROM subjects";
    private static final String QUERY_UPDATE_SUBJECTS = "UPDATE subjects SET subject_name = ? WHERE subject_name = ?";
    private static final String QUERY_DELETE_SUBJECTS = "DELETE FROM subjects WHERE subject_name = ?";
    private static final String QUERY_GET_LIST_COLLEGES = "SELECT c.college_name " +
            "FROM subjects s " +
            "JOIN college_subject сs ON s.subject_id = сs.subject_id " +
            "JOIN colleges c ON c.college_id = сs.college_id " +
            "WHERE s.subject_name = ? ";

    public void createSubject(Subject subject) {
        try (PreparedStatement pst = DatabaseUtil.getConnection().prepareStatement(QUERY_CREATE_SUBJECTS)) {
            pst.setString(1, subject.getNameSubject());
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Subject> getAllSubjects() {
        List<Subject> subjects = new ArrayList<>();
        try (PreparedStatement pst = DatabaseUtil.getConnection().prepareStatement(QUERY_READ_SUBJECTS)) {

            try (ResultSet resultSet = pst.executeQuery()) {
                while (resultSet.next()) {
                    String nameSubject = resultSet.getString("subject_name");
                    Subject subject = new Subject(nameSubject);
                    List<String> collegeList = fetchCollegeFromDatabase(subject.getNameSubject());
                    subject.setCollegeList(collegeList);
                    subjects.add(subject);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return subjects;
    }

    public void updateSubject(Subject subject, String newName) {
        try (PreparedStatement pst = DatabaseUtil.getConnection().prepareStatement(QUERY_UPDATE_SUBJECTS)) {
            pst.setString(1, newName);
            pst.setString(2, subject.getNameSubject());
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteSubject(String subjectName) {
        try (PreparedStatement pst = DatabaseUtil.getConnection().prepareStatement(QUERY_DELETE_SUBJECTS)) {
            pst.setString(1, subjectName);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Этот метод извлекает список колледжей из базы данных, в которых преподают указанный предмет.
     * @param subjectName название предмета, для которого требуется получить список колледжей
     * @return список колледжей
     */
    private List<String> fetchCollegeFromDatabase(String subjectName) {
        List<String> collegeList = new ArrayList<>();
        try (PreparedStatement pst = DatabaseUtil.getConnection().prepareStatement(QUERY_GET_LIST_COLLEGES)) {

            pst.setString(1, subjectName);

            try (ResultSet resultSet = pst.executeQuery()) {
                while (resultSet.next()) {

                    String subject = resultSet.getString("college_name");

                    collegeList.add(subject);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return collegeList;
    }
}

