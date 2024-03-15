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
    private SubjectDAO subjectDAO;

    private SubjectService() {
        subjectDAO = new SubjectDAO();
    }

    public SubjectService(SubjectDAO subjectDAO) {
        this.subjectDAO = subjectDAO;
    }

    public static SubjectService getSubjectService() {
        if (instance == null) {
            return new SubjectService();
        } else return instance;
    }

    public boolean createSubject(SubjectDTO subject) {
        if (!subjectIsExist(subject)) {
            subjectDAO.createSubject(toSubjectModel(subject));
            return true;
        }
        return false;
    }

    public List<SubjectDTO> getAllSubjects() {
        return subjectDAO.getAllSubjects().stream().map(subject -> {
                    toSubjectDto(subject);
                    return toSubjectDto(subject);
                })
                .collect(Collectors.toList());
    }

    public boolean updateSubject(String oldName, String newName) {

        if (subjectDAO.getAllSubjectsName().contains(oldName)) {
            subjectDAO.updateSubject(oldName, newName);
            return true;
        }
        return false;
    }

    public boolean deleteSubject(SubjectDTO subjectDTO) {
        Subject subject = toSubjectModel(subjectDTO);
        if (subjectIsExist(subjectDTO)) {
            subjectDAO.deleteSubject(subject.getNameSubject());
            return true;
        }
        return false;
    }

    /**
     * Этот метод проверяет существует ли в базе данных заданный предмет
     *
     * @param subjectDTO название предмета, для которого требуется получить список колледжей
     * @return true - если такой прдемет уже есть в базе данных, false - если нет
     */
    private boolean subjectIsExist(SubjectDTO subjectDTO) {
        List<SubjectDTO> subjectDTOList = getAllSubjects();
        return subjectDTOList.stream().anyMatch(s -> s.getNameSubjectDto()
                .equals(subjectDTO.getNameSubjectDto()));
    }
}
