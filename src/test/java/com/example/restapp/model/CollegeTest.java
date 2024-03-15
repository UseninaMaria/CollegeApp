package com.example.restapp.model;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CollegeTest {
    private final String TEST_NAME_COLLEGE = "Test College";
    private final String TEST_NAME_STUDENT = "Test Student";
    private final String TEST_NAME_SUBJECT= "Test Subjects";
    Student testStudent = new Student(TEST_NAME_STUDENT, 1, 3.8);
    private Subject  testSubject = new Subject(TEST_NAME_SUBJECT);
    @Test
    void testGettersAndSetters() {
        College college = new College();
        college.setRating(4.2);
        college.setNameCollege(TEST_NAME_COLLEGE);

        List<Subject> subjects = Arrays.asList(testSubject);
        college.setSubjectList(subjects);

        List<Student> students = Arrays.asList(testStudent);
        college.setStudentList(students);

        double ratingResult = college.getRating();
        String nameResult = college.getNameCollege();
        List<Subject> subjectsResult = college.getSubjectList();
        List<Student> studentsResult = college.getStudentList();

        assertEquals(4.2, ratingResult);
        assertEquals(TEST_NAME_COLLEGE, nameResult);
        assertEquals(subjects, subjectsResult);
        assertEquals(students, studentsResult);
    }

    @Test
    void testToString() {
        College college = new College(4.5, TEST_NAME_COLLEGE);
        List<Subject> subjects = Arrays.asList(testSubject);
        List<Student> students = Arrays.asList(testStudent);
        college.setSubjectList(subjects);
        college.setStudentList(students);

        String resultString = college.toString();

        assertEquals("College{rating=4.5, nameCollege='Test College'," +
                " subjectList=[Subject{nameSubject='Test Subjects', collegeList=null}]," +
                " studentList=[Student{, studentName='Test Student', collegeId=1, gpa=3.8}]}", resultString);
    }
}
