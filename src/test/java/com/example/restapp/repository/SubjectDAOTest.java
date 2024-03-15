package com.example.restapp.repository;

import com.example.restapp.model.Subject;
import com.example.restapp.utils.DatabaseConnection;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
 class SubjectDAOTest {
    private SubjectDAO subjectDAO;
    private Subject testSubject;
    private Subject updateSubject;
    private final String TEST_NAME_SUBJECT= "Test Subjects";
    private final String TEST_UPDATE_NAME_SUBJECT= "UPDATE Subjects";
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

        subjectDAO = new SubjectDAO();
        testSubject = new Subject(TEST_NAME_SUBJECT);
        updateSubject = new Subject(TEST_UPDATE_NAME_SUBJECT);
    }

    @Test
    @Order(1)
    @DisplayName("Добавление нового предмета в бд")
    void testCreateSubject() {
        subjectDAO.createSubject(testSubject);
        assertTrue(checkSubjectExists(TEST_NAME_SUBJECT));
    }

    @Test
    @Order(2)
    @DisplayName("Получение всех предметов")
    void testGetAllSubjects() {
        assertEquals(13, subjectDAO.getAllSubjectsName().size());
    }

    @Test
    @Order(3)
    @DisplayName("Обновление предмета")
    void testUpdateSubject() {
        subjectDAO.updateSubject(TEST_NAME_SUBJECT, updateSubject.getNameSubject());
        assertTrue(checkSubjectExists(TEST_UPDATE_NAME_SUBJECT));
    }

    @Test
    @Order(4)
    @DisplayName("Удаление предмета по имени")
    void testDeleteSubject() {
        subjectDAO.deleteSubject(TEST_UPDATE_NAME_SUBJECT);
        assertFalse(checkSubjectExists(TEST_UPDATE_NAME_SUBJECT));
    }

    @Test
    @Order(5)
    @DisplayName("Удаление предмета по имени")
    void testGetAllSubjectsName() {
        assertEquals(12, subjectDAO.getAllSubjectsName().size());
    }
    /**
     * Этот метод извлекает список предметов из базы данных и проверяет наличие искомого предмета..
     *
     * @param subjectName имя предмета для поиска
     * @return true, если предмет с указанным именем существует в базе данных, в противном случае - false
     */
    private boolean checkSubjectExists(String subjectName) {
       List<String> subjectList = subjectDAO.getAllSubjectsName();
        return subjectList.contains(subjectName);
    }
}
