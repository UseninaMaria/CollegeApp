package com.example.restapp.mappers;

import com.example.restapp.dto.CollegeDTO;
import com.example.restapp.model.College;


public class СollegeMapper {
    public static CollegeDTO toCollegeDto(College college) {
        CollegeDTO collegeDTO = new CollegeDTO();
        collegeDTO.setCollegeRating(college.getRating());
        collegeDTO.setNameCollege(college.getNameCollege());
        collegeDTO.setSubjectDtoList(college.getSubjectList());
        collegeDTO.setStudentDtoList(college.getStudentList());
        return collegeDTO;
    }
    public static College toCollegeModel(CollegeDTO collegeDTO) {
        College college = new College();
        college.setRating(collegeDTO.getCollegeRating());
        college.setNameCollege(collegeDTO.getNameCollege());
        college.setStudentList(collegeDTO.getStudentDtoList());
        college.setSubjectList(collegeDTO.getSubjectDtoList());
        return college;
    }
    public static CollegeDTO DTOtoDto(CollegeDTO collegeDTO) {
        CollegeDTO DTO = new CollegeDTO();
        DTO.setCollegeRating(collegeDTO.getCollegeRating());
        DTO.setNameCollege(collegeDTO.getNameCollege());
        return DTO;
    }
}
