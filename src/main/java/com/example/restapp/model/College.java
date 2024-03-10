package com.example.restapp.model;

import java.util.ArrayList;
import java.util.List;

public class College {
    private double rating;
    private String nameCollege;
    private List<Subject> subjectList;
    private List<Student> studentList;

    public College() {
    }

    public College(double rating, String nameCollege) {
        this.rating = rating;
        this.nameCollege = nameCollege;
        subjectList = new ArrayList<>();
        studentList = new ArrayList<>();
    }

    public List<Subject> getSubjectList() {
        return subjectList;
    }

    public void setSubjectList(List<Subject> subjectList) {
        this.subjectList = subjectList;
    }

    public List<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getNameCollege() {
        return nameCollege;
    }

    public void setNameCollege(String nameCollege) {
        this.nameCollege = nameCollege;
    }

    @Override
    public String toString() {
        return "College{" +
                "rating=" + rating +
                ", nameCollege='" + nameCollege + '\'' +
                ", subjectList=" + subjectList +
                ", studentList=" + studentList +
                '}';
    }
}
