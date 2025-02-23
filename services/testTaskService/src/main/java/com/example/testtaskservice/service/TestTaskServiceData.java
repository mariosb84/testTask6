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

    /*
    * Улучшенный вариант:
    * В задаче на нахождение N-го максимального элемента алгоритм QuickSelect действительно является хорошим выбором,
    *  так как он в среднем работает за O(n). Однако реализация, представленная у тебя, имеет несколько недочетов:

Недостатки реализации QuickSelect в коде:
Проблема с разбиением на left и right списки

В реализации создаются два списка, что требует дополнительной памяти O(n).
Более эффективный вариант: переставлять элементы на месте в исходном массиве.
Выбор pivot всегда последним элементом

Это может ухудшить производительность, если данные уже частично отсортированы.
Оптимальнее использовать рандомный выбор pivot или медиану трех элементов.
Обработка случая k == left.size()

В коде return pivot; используется без учета дубликатов, что может дать неверный ответ.
Как можно улучшить?
Использовать "in-place QuickSelect", который не требует доп. памяти и работает быстрее. Вот исправленная версия:

Оптимизированный QuickSelect
java
Копировать
Редактировать
private static int quickSelect(List<Integer> nums, int k) {
    return quickSelectHelper(nums, 0, nums.size() - 1, k);
}

private static int quickSelectHelper(List<Integer> nums, int left, int right, int k) {
    Random rand = new Random();

    while (left <= right) {
        int pivotIndex = left + rand.nextInt(right - left + 1);
        int pivot = nums.get(pivotIndex);
        int partitionIndex = partition(nums, left, right, pivot);

        if (partitionIndex == k) {
            return nums.get(partitionIndex);
        } else if (partitionIndex < k) {
            left = partitionIndex + 1;
        } else {
            right = partitionIndex - 1;
        }
    }
    return -1; // Этот return никогда не достигнется
}

private static int partition(List<Integer> nums, int left, int right, int pivot) {
    while (left <= right) {
        while (nums.get(left) < pivot) {
            left++;
        }
        while (nums.get(right) > pivot) {
            right--;
        }
        if (left <= right) {
            Collections.swap(nums, left, right);
            left++;
            right--;
        }
    }
    return left;
}
Почему этот вариант лучше?
Работает на месте – O(1) дополнительной памяти.
Средняя сложность O(n), что быстрее кучи O(n log n).
Использует рандомизированный выбор pivot, что избегает худших случаев O(n²).
Заключение
Да, правильный выбор был QuickSelect, но твоя реализация неоптимальна из-за дополнительных списков
*  и фиксированного pivot. Улучшенная версия работает быстрее и эффективнее по памяти.
* */

}
