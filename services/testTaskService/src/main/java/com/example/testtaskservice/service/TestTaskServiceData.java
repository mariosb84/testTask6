package com.example.testtaskservice.service;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/*import java.util.PriorityQueue;*/

@Service
public class TestTaskServiceData implements TestTaskService {

    @Override
    public ResponseEntity<Integer> getNthMaxNumber(String filePath, int n) throws IOException {
        int nthMax = findNthMax(filePath, n);
        return ResponseEntity.ok(nthMax);
    }

    /*
    * Метод с алгоритмом поиска (ИСПОЛЬЗУЕМ АЛГОРИТМ МИНИМАЛЬНОЙ КУЧИ - ОКАЗАЛСЯ НЕЭФФЕКТИВЕН):*/

    /*private static Integer findNthMax(String filePath, int N) throws IOException {
        if (filePath == null || N <= 0) {
            throw new IllegalArgumentException("Путь к файлу не может быть null и N должно быть положительным числом.");
        }

        *//* Минимальная куча*//*
        PriorityQueue<Integer> minHeap = new PriorityQueue<>(N);

        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            *//* Читаем первый лист*//*
            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                for (Cell cell : row) {
                    if (cell != null && cell.getCellType() == CellType.NUMERIC) {
                        int number = (int) cell.getNumericCellValue();

                        *//* Добавляем число в кучу*//*
                        minHeap.offer(number);

                        *//* Если размер кучи превышает N, удаляем минимальный элемент*//*
                        if (minHeap.size() > N) {
                            minHeap.poll();
                        }
                    }
                }
            }
        } catch (NullPointerException e) {
            System.err.println("Ошибка: Обнаружен null-элемент в ячейке.");

            *//* Выбрасываем исключение*//*
            throw new RuntimeException("Ошибка при обработке данных в файле.");
        }

        *//* Если меньше N чисел, выбрасываем исключение*//*
        if (minHeap.size() < N) {
            throw new RuntimeException("Недостаточно чисел для нахождения " + N + "-го максимального числа.");
        }

        *//* Возвращаем N-е максимальное число*//*
        return minHeap.peek();
    }*/


    /*
     * Метод с алгоритмом поиска (ИСПОЛЬЗУЕМ АЛГОРИТМ МИНИМАЛЬНОЙ КУЧИ - ОКАЗАЛСЯ БОЛЕЕ ЭФФЕКТИВЕН):*/

    private static Integer findNthMax(String filePath, int N) throws IOException {
        if (filePath == null || N <= 0) {
            throw new IllegalArgumentException("Путь к файлу не может быть null и N должно быть положительным числом.");
        }

        List<Integer> numbers = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            /* Читаем первый лист*/
            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                for (Cell cell : row) {
                    if (cell != null && cell.getCellType() == CellType.NUMERIC) {
                        int number = (int) cell.getNumericCellValue();
                        numbers.add(number);
                    }
                }
            }
        } catch (NullPointerException e) {
            System.err.println("Ошибка: Обнаружен null-элемент в ячейке.");
            throw new RuntimeException("Ошибка при обработке данных в файле.");
        }

        /* Если меньше N чисел, выбрасываем исключение*/
        if (numbers.size() < N) {
            throw new RuntimeException("Недостаточно чисел для нахождения " + N + "-го максимального числа.");
        }

        return quickSelect(numbers, numbers.size() - N);
    }

    private static int quickSelect(List<Integer> nums, int k) {
        if (nums.size() == 1) {
            return nums.get(0);
        }

        int pivot = nums.get(nums.size() - 1);
        List<Integer> left = new ArrayList<>();
        List<Integer> right = new ArrayList<>();

        for (int num : nums) {
            if (num < pivot) {
                left.add(num);
            } else if (num > pivot) {
                right.add(num);
            }
        }

        if (k < left.size()) {
            return quickSelect(left, k);
        } else if (k > left.size()) {
            return quickSelect(right, k - left.size() - 1);
        } else {
            /* k == left.size()*/
            return pivot;
        }
    }

}
