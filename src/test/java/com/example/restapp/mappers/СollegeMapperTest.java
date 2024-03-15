package com.example.restapp.mappers;

import com.example.restapp.dto.CollegeDTO;
import com.example.restapp.model.College;
import org.junit.jupiter.api.Test;

import static com.example.restapp.mappers.СollegeMapper.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

 class СollegeMapperTest {
    private final String TEST_NAME_COLLEGE = "Test COLLEGE";
    @Test
    void testToCollegeDto() {

        College college = new College(4.5, TEST_NAME_COLLEGE);

        CollegeDTO collegeDTO = toCollegeDto(college);

        assertEquals(4.5, collegeDTO.getCollegeRating());
        assertEquals(TEST_NAME_COLLEGE, collegeDTO.getNameCollege());

    }

    @Test
    void testToCollegeModel() {

        CollegeDTO collegeDTO = new CollegeDTO();
        collegeDTO.setCollegeRating(3.8);
        collegeDTO.setNameCollege(TEST_NAME_COLLEGE);

        College college = toCollegeModel(collegeDTO);

        assertEquals(3.8, college.getRating());
        assertEquals(TEST_NAME_COLLEGE, college.getNameCollege());
    }
}
