package com.example.restapp.service;

import com.example.restapp.dto.CollegeDTO;
import com.example.restapp.model.College;
import com.example.restapp.repository.CollegeDAO;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.restapp.mappers.СollegeMapper.toCollegeDto;
import static com.example.restapp.mappers.СollegeMapper.toCollegeModel;

public class CollegeService {
    private static CollegeService instance;
    private final static CollegeDAO COLLEGE_DAO = new CollegeDAO();

    private CollegeService() {
    }

    public static CollegeService getCollegeService() {
        if (instance == null) {
            return new CollegeService();
        } else return instance;
    }

    public boolean createCollege(CollegeDTO collegeDTO) {
        College college = toCollegeModel(collegeDTO);
        List<College> existingColleges = COLLEGE_DAO.selectAllColleges();

        if (!existingColleges.stream().anyMatch(c -> c.getNameCollege().equals(college.getNameCollege()))) {
            return COLLEGE_DAO.createCollege(college);
        }
        return false;
    }

    public List<CollegeDTO> getAllColleges() {
        return COLLEGE_DAO.selectAllColleges()
                .stream()
                .map(college -> {
                    CollegeDTO collegeDTO = toCollegeDto(college);
                    return collegeDTO;
                })
                .collect(Collectors.toList());
    }

    public CollegeDTO getCollegeByName(String nameCollege) {
        List<College> existingColleges = COLLEGE_DAO.selectAllColleges();

        if (existingColleges.stream().anyMatch(c -> c.getNameCollege().equals(nameCollege))) {
            College college = COLLEGE_DAO.selectCollegeByName(nameCollege);
            CollegeDTO collegeDTO = toCollegeDto(college);
            return collegeDTO;
        }
        return null;
    }

    public boolean updateCollegeName(String oldName, String newName) {
        List<College> existingColleges = COLLEGE_DAO.selectAllColleges();

        if (existingColleges.stream().anyMatch(c -> c.getNameCollege().equals(oldName))) {
            return COLLEGE_DAO.updateCollegeName(oldName, newName);
        }
        return false;
    }

    public boolean updateCollegeRating(String nameCollege, double rating) {
        List<College> existingColleges = COLLEGE_DAO.selectAllColleges();

        if (existingColleges.stream().anyMatch(c -> c.getNameCollege()
                .equals(nameCollege) && c.getRating() == rating)) {
            return COLLEGE_DAO.updateCollegeRating(nameCollege, rating);
        }
        return false;
    }

    public boolean deleteCollege(String name) {
        List<College> existingColleges = COLLEGE_DAO.selectAllColleges();

        if (existingColleges.stream().anyMatch(c -> c.getNameCollege().equals(name))) {
            return COLLEGE_DAO.deleteCollege(name);
        }
        return false;
    }
}
