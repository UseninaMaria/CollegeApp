package com.example.restapp.dto;

import java.util.List;

public class SubjectDTO {
    private String nameSubjectDto;
    private List<String> collegeListDto;

    public String getNameSubjectDto() {
        return nameSubjectDto;
    }

    public void setNameSubjectDto(String nameSubjectDto) {
        this.nameSubjectDto = nameSubjectDto;

    }
    public List<String> getCollegeListDto() {
        return collegeListDto;
    }

    public void setCollegeListDto(List<String> collegeListDto) {
        this.collegeListDto = collegeListDto;
    }

    @Override
    public String toString() {
        return "SubjectDTO{" +
                "nameSubjectDto='" + nameSubjectDto + '\'' +
                ", collegeListDto=" + collegeListDto +

                '}';
    }
}
