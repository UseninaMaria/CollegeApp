package com.example.restapp.dto;


public class StudentDTO {
    private String studentNameDto;
    private int collegeId;
    private double gpa;

    public String getStudentNameDto() {
        return studentNameDto;
    }

    public void setStudentNameDto(String studentNameDto) {
        this.studentNameDto = studentNameDto;
    }

    public int getCollegeId() {
        return collegeId;
    }

    public void setCollegeId(int college) {
        this.collegeId = college;
    }

    public double getGpa() {
        return gpa;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }

    @Override
    public String toString() {
        return "StudentDTO{" +
                "studentNameDto='" + studentNameDto + '\'' +
                ", collegeId=" + collegeId +
                ", gpa=" + gpa +
                '}';
    }
}
