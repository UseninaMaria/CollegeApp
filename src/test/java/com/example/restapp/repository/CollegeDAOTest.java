package com.example.restapp.repository;


import com.example.restapp.model.College;
import com.example.restapp.utils.DatabaseConnection;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CollegeDAOTest {
    private CollegeDAO collegeDAO;
    private College testCollege;
    private final String TEST_NAME_COLLEGE = "Test College";
    private final String TEST_UPDATE_NAME_COLLEGE = "New College Name";
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

        testCollege = new College(9.0, TEST_NAME_COLLEGE);
        collegeDAO = new CollegeDAO(dbConnector);
    }

    @Test
    @Order(1)
    @DisplayName("Добавление нового колледжа в бд")
    void testCreateCollege() {

        boolean created = collegeDAO.createCollege(testCollege);
        assertTrue(created);

        College createdCollege = collegeDAO.selectCollegeByName(TEST_NAME_COLLEGE);

        assertNotNull(createdCollege);
        assertEquals("Test College", createdCollege.getNameCollege());
        assertEquals(9.0, createdCollege.getRating());
    }

    @Test
    @DisplayName("Получение колледжа по имени")
    @Order(2)
    void testSelectCollege() {
        College college = collegeDAO.selectCollegeByName(TEST_NAME_COLLEGE);
        assertNotNull(college);

        assertEquals(TEST_NAME_COLLEGE, college.getNameCollege());
    }

    @Test
    @DisplayName("Обновление имени колледжа в бд")
    @Order(3)
    void testUpdateCollegeName() {
        boolean updated = collegeDAO.updateCollegeName(TEST_NAME_COLLEGE, TEST_UPDATE_NAME_COLLEGE);
        assertTrue(updated);

        College updatedCollege = collegeDAO.selectCollegeByName(TEST_UPDATE_NAME_COLLEGE);
        assertNotNull(updatedCollege);
        assertEquals(TEST_UPDATE_NAME_COLLEGE, updatedCollege.getNameCollege());
    }

    @Test
    @DisplayName("Обновление рейтинга колледжа в бд")
    @Order(4)
    void testUpdateCollegeRating() {
        boolean updated = collegeDAO.updateCollegeRating(TEST_UPDATE_NAME_COLLEGE, 5.0);
        assertTrue(updated);

        College createdCollege = collegeDAO.selectCollegeByName(TEST_UPDATE_NAME_COLLEGE);
        assertNotNull(createdCollege);
        assertEquals(5.0, createdCollege.getRating());
    }

    @Test
    @DisplayName("Удаление колледжа из бд")
    @Order(5)
    void testDeleteCollege() {
        boolean deleted = collegeDAO.deleteCollege(TEST_UPDATE_NAME_COLLEGE);
        assertTrue(deleted);

        College updatedCollege = collegeDAO.selectCollegeByName(TEST_UPDATE_NAME_COLLEGE);
        assertNull(updatedCollege);
    }

    @Test
    @DisplayName("Получение списка всех колледжей из бд")
    @Order(6)
    void testSelectAllColleges() {
        List<College> colleges = collegeDAO.selectAllColleges();
        assertNotNull(colleges);
        assertEquals(4, colleges.size());
    }

    @DisplayName("Удаление несуществующего колледжа из бд")
    @Order(7)
    @Test
    void testDeleteNonExistentCollege() {
        CollegeDAO collegeDAO = new CollegeDAO();

        boolean result = collegeDAO.deleteCollege("Non-existent College");

        assertTrue(result);
    }

}
