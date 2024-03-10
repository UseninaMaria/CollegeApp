package com.example.restapp.dto;

import com.example.restapp.model.Student;
import com.example.restapp.model.Subject;

import java.util.List;

public class CollegeDTO {
    private double collegeRating;
    private String nameCollege;
    private List<Subject> subjectDtoList;
    private List<Student> studentDtoList;

    public List<Subject> getSubjectDtoList() {
        return subjectDtoList;
    }

    public void setSubjectDtoList(List<Subject> subjectDtoList) {
        this.subjectDtoList = subjectDtoList;
    }

    public double getCollegeRating() {
        return collegeRating;
    }

    public void setCollegeRating(double collegeRating) {
        this.collegeRating = collegeRating;
    }

    public String getNameCollege() {
        return nameCollege;
    }

    public List<Student> getStudentDtoList() {
        return studentDtoList;
    }

    public void setStudentDtoList(List<Student> studentDtoList) {
        this.studentDtoList = studentDtoList;
    }

    public void setNameCollege(String nameCollege) {
        this.nameCollege = nameCollege;
    }

    @Override
    public String toString() {
        return "CollegeDTO{" +
                "collegeRating=" + collegeRating +
                ", nameCollege='" + nameCollege + '\'' +
                ", subjectDtoList=" + subjectDtoList +
                ", studentDtoList=" + studentDtoList +
                '}';
    }
}
