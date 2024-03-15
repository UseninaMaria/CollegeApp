package com.example.restapp.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

 class StudentTest {
    private final String TEST_NAME_STUDENT = "Test Student";
    @Test
    void testGettersAndSetters() {
        Student student = new Student();
        student.setStudentName(TEST_NAME_STUDENT);
        student.setCollegeId(123);
        student.setGpa(3.5);

        String nameResult = student.getStudentName();
        int collegeIdResult = student.getCollegeId();
        double gpaResult = student.getGpa();

        assertEquals(TEST_NAME_STUDENT, nameResult);
        assertEquals(123, collegeIdResult);
        assertEquals(3.5, gpaResult);
    }

    @Test
    void testToString() {

        Student student = new Student(TEST_NAME_STUDENT, 456, 3.8);

        String resultString = student.toString();

        assertEquals("Student{studentName='Test Student', collegeId=456, gpa=3.8}", resultString);
    }
}
