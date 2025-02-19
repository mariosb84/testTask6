package com.example.testtaskservice;

import com.example.testtaskservice.service.TestTaskServiceData;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertThrows;

class TestTaskServiceDataMockTest {

    @Test
    void testGetNthMaxNumber_EmptyFile() throws IOException {
        /* Создание мока для Workbook и XSSFSheet*/
        XSSFSheet sheet;
        try (XSSFWorkbook workbook = Mockito.mock(XSSFWorkbook.class)) {
            sheet = Mockito.mock(XSSFSheet.class);

            /* Настройка поведения моков*/
            Mockito.when(workbook.getSheetAt(0)).thenReturn(sheet);
        }
        /* Пустой итератор*/
        Mockito.when(sheet.iterator()).thenReturn(Collections.emptyIterator());

        /* Создание сервиса с использованием мока*/
        TestTaskServiceData service = Mockito.mock(TestTaskServiceData.class);

        /* Замокировать вызов, который открывает файл*/
        Mockito.when(service.getNthMaxNumber("mockedPath", 1)).thenThrow(new RuntimeException("File not found!"));

        /* Проверка на выбрасывание исключения при пустом файле*/
        assertThrows(RuntimeException.class, () -> {
            service.getNthMaxNumber("mockedPath", 1);
        });
    }

    @Test
    void testGetNthMaxNumber_InsufficientNumbers() throws IOException {
        /* Создание мока для Workbook и XSSFSheet*/
        XSSFSheet sheet;
        Row row;
        Cell cell;
        try (XSSFWorkbook workbook = Mockito.mock(XSSFWorkbook.class)) {
            sheet = Mockito.mock(XSSFSheet.class);
            row = Mockito.mock(Row.class);
            cell = Mockito.mock(Cell.class);

            /* Настройка поведения моков*/
            Mockito.when(workbook.getSheetAt(0)).thenReturn(sheet);
        }
        Mockito.when(sheet.iterator()).thenReturn(Collections.singletonList(row).iterator());
        Mockito.when(row.iterator()).thenReturn(Collections.singletonList(cell).iterator());
        Mockito.when(cell.getCellType()).thenReturn(CellType.NUMERIC);
        /* Только одно число*/
        Mockito.when(cell.getNumericCellValue()).thenReturn(1.0);

        /* Создание сервиса с использованием мока*/
        TestTaskServiceData service = Mockito.mock(TestTaskServiceData.class);

        /* Замокировать вызов, который открывает файл*/
        Mockito.when(service.getNthMaxNumber("mockedPath", 2)).
                thenThrow(new RuntimeException("Insufficient numbers!"));

        /* Проверка на недостаточное количество чисел*/
        assertThrows(RuntimeException.class, () -> {
            /* Запрашиваем 2-е максимальное число*/
            service.getNthMaxNumber("mockedPath", 2);
        });
    }

}

