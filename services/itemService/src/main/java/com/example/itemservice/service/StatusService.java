package com.example.itemservice.service;

import com.example.itemservice.domain.Status;

import java.util.List;
import java.util.Optional;

public interface StatusService {

    List<Status> findAll();

    Optional<Status> add(Status status);

    boolean update(Status status);

    Optional<Status> findById(int id);

    boolean delete(Status status);

}
