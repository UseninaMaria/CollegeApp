package com.example.restapp.service;

import com.example.restapp.dto.StudentDTO;
import com.example.restapp.model.Student;
import com.example.restapp.repository.StudentDAO;
import com.example.restapp.utils.DatabaseConnection;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

import static com.example.restapp.mappers.StudentMapper.toStudentDto;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
 class StudentServiceTest {
    private final String TEST_STUDENT_NAME = "Test Student";
    private final String TEST_STUDENT_EXIST_NAME = "Doja Cat";
    private final String TEST_STUDENT_UPDATE_NAME =  "Update Student";
    private Student studentTest;
    private Student  studentUpdateTest;
    private StudentDTO studentDtoTest;
    private StudentDTO studentDtoUpdateTest;;
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
    @Mock
    private StudentDAO studentMockDao;

    @InjectMocks
    private StudentService studentMockService;

    @BeforeEach
    void setUp() {
        DatabaseConnection dbConnector = new DatabaseConnection(postgres.getDriverClassName(),
                postgres.getJdbcUrl(), postgres.getUsername(), postgres.getPassword());

        studentMockDao = mock(StudentDAO.class);
        studentTest = new Student(TEST_STUDENT_NAME, 2,8.8);
        studentDtoTest = toStudentDto(studentTest);

        studentUpdateTest =  new Student(TEST_STUDENT_UPDATE_NAME, 3,5.0);
        studentDtoUpdateTest = toStudentDto(studentUpdateTest);

        studentMockService = new StudentService(studentMockDao);
    }

    @Test
    @Order(1)
    @DisplayName("Добавление нового студента в бд")
    void testCreateStudent() {
        when(studentMockDao.selectStudentByName(TEST_STUDENT_NAME)).thenReturn(false);

        boolean result = studentMockService.createStudent(studentDtoTest);

        assertTrue(result);
        verify(studentMockDao).selectStudentByName(TEST_STUDENT_NAME);
        verify(studentMockDao).createStudent(any(Student.class));
    }

    @Test
    @Order(2)
    @DisplayName("Получения студента из бд")
    void testGetStudentByName() {
        when(studentMockDao.selectStudent(TEST_STUDENT_EXIST_NAME))
                .thenReturn(new Student(TEST_STUDENT_EXIST_NAME,4,9.8));

        StudentDTO result = studentMockService.getStudentByName(TEST_STUDENT_EXIST_NAME);

        assertNotNull(result);
        assertEquals(TEST_STUDENT_EXIST_NAME, result.getStudentNameDto());
        assertEquals(4, result.getCollegeId());
        verify(studentMockDao).selectStudent(TEST_STUDENT_EXIST_NAME);
    }

    @Test
    @Order(3)
    @DisplayName("Обновление студента в бд")
    void testUpdateStudent() {

        when(studentMockDao.selectStudentByName(TEST_STUDENT_NAME)).thenReturn(true);

        boolean result = studentMockService.updateStudent(studentDtoUpdateTest, TEST_STUDENT_NAME);

        assertTrue(result);
        verify(studentMockDao).selectStudentByName(TEST_STUDENT_NAME);
        verify(studentMockDao).updateStudent(any(Student.class), eq(TEST_STUDENT_NAME));
    }

    @Test
    @Order(4)
    @DisplayName("Удаление студента из бд")
    void testDeleteStudent() {
        when(studentMockDao.selectStudentByName("Dmitry Volkov")).thenReturn(true);

        boolean result = studentMockService.deleteStudent("Dmitry Volkov");

        assertTrue(result);
        verify(studentMockDao).selectStudentByName("Dmitry Volkov");
        verify(studentMockDao).deleteStudent("Dmitry Volkov");
    }
    }
