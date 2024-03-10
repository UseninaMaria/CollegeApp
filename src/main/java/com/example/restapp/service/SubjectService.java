package com.example.restapp.service;

import com.example.restapp.dto.SubjectDTO;
import com.example.restapp.model.Subject;
import com.example.restapp.repository.SubjectDAO;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.restapp.mappers.SubjectMapper.toSubjectDto;
import static com.example.restapp.mappers.SubjectMapper.toSubjectModel;

public class SubjectService {
    private static SubjectService instance;
    private final static SubjectDAO SUBJECT_DAO = new SubjectDAO();

    private SubjectService() {
    }

    public static SubjectService getSubjectService() {
        if (instance == null) {
            return new SubjectService();
        } else return instance;
    }

    public void createSubject(SubjectDTO subject) {
        if (!subjectIsExist(subject)) {
            SUBJECT_DAO.createSubject(toSubjectModel(subject));
        }
        //Проверить метод equals, как он переопределен
    }

    public List<SubjectDTO> getAllSubjects() {
        return SUBJECT_DAO.getAllSubjects().stream().map(subject -> {
                    toSubjectDto(subject);
                    return toSubjectDto(subject);
                })
                .collect(Collectors.toList());
    }

    public boolean updateSubject(SubjectDTO subjectDTO, String newName) {
        Subject subject = toSubjectModel(subjectDTO);
        if (subjectIsExist(subjectDTO)) {
            SUBJECT_DAO.updateSubject(subject, newName);
            return true;
        }
        SUBJECT_DAO.createSubject(subject);
        return false;
    }

    public boolean deleteSubject(SubjectDTO subjectDTO) {
        Subject subject = toSubjectModel(subjectDTO);
        if (subjectIsExist(subjectDTO)) {
            SUBJECT_DAO.deleteSubject(subject.getNameSubject());
            return true;
        }
        return false;
    }

    private boolean subjectIsExist(SubjectDTO subjectDTO) {
        List<SubjectDTO> subjectDTOList = getAllSubjects();
        return subjectDTOList.stream().anyMatch(s -> s.getNameSubjectDto()
                .equals(subjectDTO.getNameSubjectDto()));
    }
}
