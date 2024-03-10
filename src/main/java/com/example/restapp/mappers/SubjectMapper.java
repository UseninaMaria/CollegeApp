package com.example.restapp.mappers;

import com.example.restapp.dto.SubjectDTO;
import com.example.restapp.model.Subject;

public class SubjectMapper {
    public static SubjectDTO toSubjectDto(Subject subject) {
        SubjectDTO subjectDto = new SubjectDTO();

        subjectDto.setNameSubjectDto(subject.getNameSubject());
        subjectDto.setCollegeListDto(subject.getCollegeList());
        return subjectDto;
    }

    public static Subject toSubjectModel(SubjectDTO subjectDto) {
        Subject subject = new Subject();

        subject.setNameSubject(subjectDto.getNameSubjectDto());
        subject.setCollegeList(subjectDto.getCollegeListDto());
        return subject;
    }
}
