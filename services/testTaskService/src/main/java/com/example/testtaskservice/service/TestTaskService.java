package com.example.testtaskservice.service;

import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface TestTaskService {

     ResponseEntity<Integer> getNthMaxNumber(String filePath, int n) throws IOException;

}
