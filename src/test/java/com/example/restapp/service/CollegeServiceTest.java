package com.example.restapp.service;

import com.example.restapp.dto.CollegeDTO;
import com.example.restapp.model.College;
import com.example.restapp.repository.CollegeDAO;
import com.example.restapp.utils.DatabaseConnection;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

import java.util.List;

import static com.example.restapp.mappers.СollegeMapper.toCollegeDto;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
 class CollegeServiceTest {
    private final String TEST_COLLEGE_NAME = "New College";
    private final String TEST_COLLEGE_EXIST_NAME = "Technical College of Innovations";
    College collegeTest;
    College collegeExistsTest;
    CollegeDTO collegeExistsDtoTest;
    CollegeDTO collegeDtoTest;
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

        collegeTest = new College(4.5, TEST_COLLEGE_NAME);
        collegeDtoTest = toCollegeDto(collegeTest);
        collegeExistsTest = new College(10.0, TEST_COLLEGE_EXIST_NAME);
        collegeExistsDtoTest = toCollegeDto(collegeExistsTest);
    }

    @Mock
    private CollegeDAO collegeMockDao;

    @InjectMocks
    private CollegeService collegeMockService = CollegeService.getCollegeService();

    @Test
    @Order(1)
    void testCreateCollege() {
        boolean result = collegeMockService.createCollege(collegeDtoTest);
        assertTrue(result);
    }

    @Test
    @Order(2)
    void testCreateDuplicateCollege() {
        boolean duplicateCreated = collegeMockService.createCollege(collegeExistsDtoTest);
        assertFalse(duplicateCreated);
    }

    @Test
    @Order(3)
    @DisplayName("Поиск колледжа по имени")
    void testGetCollegeByName() {
        when(collegeMockDao.selectCollegeByName(TEST_COLLEGE_EXIST_NAME)).thenReturn(collegeExistsTest);

        CollegeDTO collegeDTO = collegeMockService.getCollegeByName(TEST_COLLEGE_EXIST_NAME);

        assertEquals(collegeExistsTest.getNameCollege(), collegeDTO.getNameCollege());
        verify(collegeMockDao).selectCollegeByName(TEST_COLLEGE_EXIST_NAME);
    }

    @Test
    @Order(4)
    @DisplayName("Получения списка колледжей")
    void testGetAllColleges() {
        List<CollegeDTO> colleges = collegeMockService.getAllColleges();
        assertNotNull(colleges);
    }

    @Test
    @Order(5)
    @DisplayName("Получения несуществующего колледжа")
    void testGetNonexistentCollegeByName() {
        CollegeDTO nonExistentCollege = collegeMockService.getCollegeByName("Non-existent College");
        assertNull(nonExistentCollege);
    }

    @Test
    @Order(6)
    @DisplayName("Обновление имени колледжа")
    void testUpdateCollegeName() {
        boolean updated = collegeMockService.updateCollegeName(collegeExistsDtoTest.getNameCollege(), "UpdateName");
        CollegeDTO collegeUpdateDTO = collegeMockService.getCollegeByName(collegeExistsDtoTest.getNameCollege());

        assertEquals("UpdateName", collegeUpdateDTO.getNameCollege());
        assertTrue(updated);
    }

    @Test
    @Order(7)
    @DisplayName("Обновление рейтинга колледжа")
    void testUpdateCollegeRating() {
        boolean updated = collegeMockService.updateCollegeRating(collegeExistsDtoTest.getNameCollege(), 5.0);
        assertTrue(updated);
    }

    @Test
    @Order(8)
    @DisplayName("Обновление рейтинга несуществующего колледжа")
    void testUpdateNonExistentCollegeRating() {
        boolean nonExistentUpdated = collegeMockService.updateCollegeRating("Non-existent College", 5.7);
        assertFalse(nonExistentUpdated);
    }

    @Test
    @Order(9)
    @DisplayName("Удаление колледжа по имени")
    void testDeleteCollege() {
        boolean deleted = collegeMockService.deleteCollege(collegeExistsDtoTest.getNameCollege());
        assertTrue(deleted);
    }

    @Test
    @Order(10)
    @DisplayName("Удаление несуществующего колледжа по имени")
    void testDeleteNonExistentCollege() {
        boolean nonExistentDeleted = collegeMockService.deleteCollege("Non-existent College Name");
        assertFalse(nonExistentDeleted);
    }
}
