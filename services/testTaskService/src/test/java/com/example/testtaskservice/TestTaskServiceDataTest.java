package com.example.testtaskservice;

import com.example.testtaskservice.service.TestTaskServiceData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TestTaskServiceDataTest {

    private TestTaskServiceData testTaskService;

    @BeforeEach
    void setUp() {
        testTaskService = new TestTaskServiceData();
    }

    @Test
    void testGetNthMaxNumber_ValidInput() throws IOException {
        /* Создание тестового Excel файла*/
        String filePath = "src/test/resources/test.xlsx";
        /* Проверка N-го максимального числа*/
        ResponseEntity<Integer> response = testTaskService.getNthMaxNumber(filePath, 2);
        /* Предполагая, что 28 - это 2-е максимальное число*/
        assertEquals(28, response.getBody());
    }

    @Test
    void testGetNthMaxNumber_InvalidFilePath() {
        /* Неправильный путь к  Excel файлу */
        assertThrows(IOException.class, () -> {
            testTaskService.getNthMaxNumber("invalid/path/to/file.xlsx", 2);
        });
    }

    @Test
    void testGetNthMaxNumber_InsufficientNumbers() throws IOException {
        /* Создание тестового Excel файла с недостаточным количеством чисел*/
        String filePath = "src/test/resources/test.xlsx";
        assertThrows(RuntimeException.class, () -> {
            testTaskService.getNthMaxNumber(filePath, 9);
        });
    }

}

