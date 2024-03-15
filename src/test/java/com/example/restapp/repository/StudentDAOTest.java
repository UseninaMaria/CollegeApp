package com.example.restapp.repository;

import com.example.restapp.model.Student;
import com.example.restapp.utils.DatabaseConnection;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
 class StudentDAOTest {
    StudentDAO studentDAO;
    Student testStudent;
    Student updatedStudent;
    Student nonExistentStudent;
    private final String TEST_NAME_STUDENT = "Test Student";
    private final String TEST_UPDATE_NAME_STUDENT = "Update Student";
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

        studentDAO = new StudentDAO();
        testStudent = new Student(TEST_NAME_STUDENT, 1, 3.8);
        updatedStudent = new Student(TEST_UPDATE_NAME_STUDENT, 2, 5.9);
        nonExistentStudent = new Student("Non-existent Student", 1, 3.7);
    }

    @Test
    @Order(1)
    @DisplayName("Добавление нового студента в бд")
    void testCreateStudent() {
        studentDAO.createStudent(testStudent);
        assertTrue(studentDAO.selectStudentByName(TEST_NAME_STUDENT));
    }

    @Test
    @Order(2)
    @DisplayName("Получение студента из бд")
    void testSelectStudent() {
        Student selectStudent = studentDAO.selectStudent(TEST_NAME_STUDENT);
        assertEquals(TEST_NAME_STUDENT, selectStudent.getStudentName());
    }

    @Test
    @Order(3)
    @DisplayName("Обновление студента в бд")
    void testUpdateStudent() {

        updatedStudent = new Student(TEST_UPDATE_NAME_STUDENT, 2, 5.9);
        Student result = studentDAO.updateStudent(updatedStudent, TEST_NAME_STUDENT);

        assertEquals(TEST_UPDATE_NAME_STUDENT, result.getStudentName());
        assertEquals(2, result.getCollegeId());
        assertEquals(5.9, result.getGpa());
    }

    @Test
    @Order(4)
    @DisplayName("Обновление несуществующего студента из бд")
    void testUpdateNonExistentStudent() {
        studentDAO.updateStudent(nonExistentStudent, nonExistentStudent.getStudentName());

        assertFalse(studentDAO.selectStudentByName(nonExistentStudent.getStudentName()));
    }

    @Test
    @Order(5)
    @DisplayName("Удаление студента из бд")
    void testDeleteStudent() {

        assertTrue(studentDAO.deleteStudent(TEST_UPDATE_NAME_STUDENT));
        assertFalse(studentDAO.selectStudentByName(TEST_UPDATE_NAME_STUDENT));
    }
}
