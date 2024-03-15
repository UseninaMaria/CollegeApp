package com.example.restapp.mappers;

import com.example.restapp.dto.SubjectDTO;
import com.example.restapp.model.Subject;
import org.junit.jupiter.api.Test;

import static com.example.restapp.mappers.SubjectMapper.toSubjectDto;
import static com.example.restapp.mappers.SubjectMapper.toSubjectModel;
import static org.junit.jupiter.api.Assertions.assertEquals;

 class SubjectMapperTest {
    private final String TEST_NAME_SUBJECT = "Test Subject";

    @Test
    void testToSubjectDto() {

        Subject subject = new Subject(TEST_NAME_SUBJECT);

        SubjectDTO subjectDTO = toSubjectDto(subject);

        assertEquals(TEST_NAME_SUBJECT, subjectDTO.getNameSubjectDto());
    }

    @Test
    void testToSubjectModel() {

        SubjectDTO subjectDTO = new SubjectDTO();
        subjectDTO.setNameSubjectDto(TEST_NAME_SUBJECT);

        Subject subject = toSubjectModel(subjectDTO);

        assertEquals(TEST_NAME_SUBJECT, subject.getNameSubject());
    }
}
