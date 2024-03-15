package com.example.restapp.service;

import com.example.restapp.dto.SubjectDTO;
import com.example.restapp.model.Subject;
import com.example.restapp.repository.SubjectDAO;
import com.example.restapp.utils.DatabaseConnection;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

import java.util.Arrays;
import java.util.List;

import static com.example.restapp.mappers.SubjectMapper.toSubjectDto;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SubjectServiceTest {
    private Subject testSubject;
    private Subject updateSubject;
    private SubjectDTO testSubjectDto;
    private SubjectDTO updateSubjectDto;
    private final String TEST_NAME_SUBJECT= "Test Subjects";
    private final String TEST_NAME_EXIST_SUBJECT= "History";
    private final String TEST_UPDATE_NAME_SUBJECT= "Update Subjects";
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(DockerImageName.parse("postgres:16.2-alpine3.19"))
            .withInitScript("test-schema.sql");

    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }


    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    @BeforeEach
    void setUp() {
        DatabaseConnection dbConnector = new DatabaseConnection(postgres.getDriverClassName(),
                postgres.getJdbcUrl(), postgres.getUsername(), postgres.getPassword());

        subjectMockDao = new SubjectDAO();
        testSubject = new Subject(TEST_NAME_SUBJECT);
        testSubjectDto = toSubjectDto(testSubject);
        updateSubject = new Subject(TEST_UPDATE_NAME_SUBJECT);
        updateSubjectDto = toSubjectDto(updateSubject);

        subjectMockDao = mock(SubjectDAO.class);
        subjectMockService = new SubjectService(subjectMockDao);
    }
    @Mock
    private SubjectDAO subjectMockDao;

    @InjectMocks
    private SubjectService subjectMockService;

    @Test
    void testCreateSubject() {
        Subject test = new Subject("Test");
        when(subjectMockDao.getAllSubjects()).thenReturn(Arrays.asList(test));

        subjectMockService.createSubject(testSubjectDto);

        verify(subjectMockDao).createSubject(test);
    }

    @Test
    void testGetAllSubjects() {
        List<Subject> subjects = Arrays.asList(new Subject("Math"), new Subject("Physics"));
        when(subjectMockDao.getAllSubjects()).thenReturn(subjects);

        List<SubjectDTO> result = subjectMockService.getAllSubjects();

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void testUpdateSubject() {
        when(subjectMockDao.getAllSubjects()).thenReturn(Arrays.asList(new Subject(TEST_NAME_EXIST_SUBJECT)));

        boolean result = subjectMockService.updateSubject(TEST_NAME_EXIST_SUBJECT, TEST_UPDATE_NAME_SUBJECT);

        assertTrue(result);
        verify(subjectMockDao).updateSubject(String.valueOf(any(Subject.class)), eq(TEST_NAME_EXIST_SUBJECT));
    }

    @Test
    void testDeleteSubject() {
        SubjectDTO subjectDTO = new SubjectDTO();
        subjectDTO.setNameSubjectDto("Geography");
        when(subjectMockDao.getAllSubjects()).thenReturn(Arrays.asList(new Subject("Geography")));

        boolean result = subjectMockService.deleteSubject(subjectDTO);

        assertTrue(result);
        verify(subjectMockDao).deleteSubject("Geography");
    }
}
