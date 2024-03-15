package com.example.restapp.service;

import com.example.restapp.dto.CollegeDTO;
import com.example.restapp.model.College;
import com.example.restapp.repository.CollegeDAO;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.restapp.mappers.СollegeMapper.*;

public class CollegeService {
    private static CollegeService instance;
    private final CollegeDAO collegeDAO;

    private CollegeService() {
        collegeDAO = new CollegeDAO();
    }

    public CollegeService(CollegeDAO collegeDAO) {
        this.collegeDAO = collegeDAO;
    }

    public static CollegeService getCollegeService() {
        if (instance == null) {
            return new CollegeService();
        } else return instance;
    }

    public boolean createCollege(CollegeDTO collegeDTO) {
        College college = toCollegeModel(collegeDTO);
        List<College> existingColleges = collegeDAO.selectAllColleges();

        if (!existingColleges.stream().anyMatch(c -> c.getNameCollege().equals(college.getNameCollege()))) {
            return collegeDAO.createCollege(college);
        }
        return false;
    }

    public List<CollegeDTO> getAllColleges() {
        return collegeDAO.selectAllColleges()
                .stream()
                .map(college -> {
                    CollegeDTO collegeDTO = toCollegeDto(college);
                    return DTOtoDto(collegeDTO);
                })
                .collect(Collectors.toList());
    }

    public CollegeDTO getCollegeByName(String nameCollege) {
        List<College> existingColleges = collegeDAO.selectAllColleges();

        if (existingColleges.stream().anyMatch(c -> c.getNameCollege().equals(nameCollege))) {
            College college = collegeDAO.selectCollegeByName(nameCollege);
            return toCollegeDto(college);
        }
        return null;
    }

    public boolean updateCollegeName(String oldName, String newName) {
        List<College> existingColleges = collegeDAO.selectAllColleges();

        if (existingColleges.stream().anyMatch(c -> c.getNameCollege().equals(oldName))) {
            return collegeDAO.updateCollegeName(oldName, newName);
        }
        return false;
    }

    public boolean updateCollegeRating(String nameCollege, double rating) {
        List<College> existingColleges = collegeDAO.selectAllColleges();

        if (existingColleges.stream().anyMatch(c -> c.getNameCollege()
                .equals(nameCollege))) {
            return collegeDAO.updateCollegeRating(nameCollege, rating);
        }
        return false;
    }

    public boolean addSubjectInCollege(String nameCollege, String nameSubject) {
        return collegeDAO.addCollegeListSubject(nameCollege, nameSubject);
    }

    public boolean deleteCollege(String name) {
        List<College> existingColleges = collegeDAO.selectAllColleges();

        if (existingColleges.stream().anyMatch(c -> c.getNameCollege().equals(name))) {
            return collegeDAO.deleteCollege(name);
        }
        return false;
    }
}
