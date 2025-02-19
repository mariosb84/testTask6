package com.example.testtaskservice.controller;

import com.example.testtaskservice.service.TestTaskService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@AllArgsConstructor
@RestController
public class TestTaskController {

    private final TestTaskService testTaskService;

    @PostMapping("/getMax")
    public ResponseEntity<Integer> getNthMaxNumber(@RequestParam("filePath") String filePath,
                                                   @RequestParam("n") int n) throws IOException {
       return testTaskService.getNthMaxNumber(filePath, n);
    }

}
