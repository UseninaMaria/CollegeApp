package com.example.restapp.model;


import java.util.ArrayList;
import java.util.List;

public class Subject {
    private String nameSubject;
    private List<String> collegeList;

    public Subject() {
    }

    public Subject(String nameSubject ) {
        this.nameSubject = nameSubject;
    }
    public Subject(String nameSubject,List<String> collegeList ) {
        this.nameSubject = nameSubject;
        this.collegeList = new ArrayList<>();
    }

    public List<String> getCollegeList() {
        return collegeList;
    }

    public void setCollegeList(List<String> collegeList) {
        this.collegeList = collegeList;
    }

    public String getNameSubject() {
        return nameSubject;
    }

    public void setNameSubject(String nameSubject) {
        this.nameSubject = nameSubject;
    }

    @Override
    public String toString() {
        return "Subject{" +
                "nameSubject='" + nameSubject + '\'' +
                ", collegeList=" + collegeList +
                '}';
    }
}
