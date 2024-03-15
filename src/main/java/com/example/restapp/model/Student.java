package com.example.restapp.model;


public class Student {
    private String studentName;
    private int collegeId;
    private double gpa;

    public Student() {
    }

    public Student(String studentName, int collegeId, double gpa) {
        this.studentName = studentName;
        this.collegeId = collegeId;
        this.gpa = gpa;
    }


    public int getCollegeId() {
        return collegeId;
    }

    public void setCollegeId(int collegeId) {
        this.collegeId = collegeId;
    }

    public double getGpa() {
        return gpa;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentName='" + studentName + '\'' +
                ", collegeId=" + collegeId +
                ", gpa=" + gpa +
                '}';
    }
}
