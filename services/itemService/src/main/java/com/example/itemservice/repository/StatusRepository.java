package com.example.itemservice.repository;

import com.example.itemservice.domain.Status;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StatusRepository extends CrudRepository<Status, Integer> {

    List<Status> findAll();

}
