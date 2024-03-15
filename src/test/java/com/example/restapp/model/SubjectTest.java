package com.example.restapp.model;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SubjectTest {

    @Test
    void testGetCollegeList() {
        Subject subject = new Subject("Math");
        List<String> collegeList = Arrays.asList("College1", "College2");
        subject.setCollegeList(collegeList);

        List<String> result = subject.getCollegeList();

        assertEquals(collegeList, result);
    }

    @Test
    void testSetCollegeList() {
        Subject subject = new Subject("Science");
        List<String> collegeList = Arrays.asList("College3", "College4");

        subject.setCollegeList(collegeList);

        assertEquals(collegeList, subject.getCollegeList());
    }

    @Test
    void testGetNameSubject() {
        Subject subject = new Subject("History");

        String result = subject.getNameSubject();

        assertEquals("History", result);
    }

    @Test
    void testSetNameSubject() {
        Subject subject = new Subject();

        subject.setNameSubject("English");

        assertEquals("English", subject.getNameSubject());
    }

    @Test
    void testToString() {
        Subject subject = new Subject("Geography");
        subject.setCollegeList(Arrays.asList("College5", "College6"));
        String result = subject.toString();

        assertEquals("Subject{nameSubject='Geography', collegeList=[College5, College6]}", result);
    }
}
