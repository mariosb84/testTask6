package com.example.itemservice.service;

import com.example.itemservice.domain.Status;
import com.example.itemservice.repository.StatusRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class StatusServiceData implements StatusService {

    private final StatusRepository statusRepository;
    @Override
    public List<Status> findAll() {
        return statusRepository.findAll();
    }

    @Override
    public Optional<Status> add(Status status) {
        Status result = statusRepository.save(status);
        if (Optional.of(result).isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(result);
    }

    @Override
    public boolean update(Status status) {
        statusRepository.save(status);
        return statusRepository.findById(status.getId()).isPresent();
    }

    @Override
    public Optional<Status> findById(int id) {
        return statusRepository.findById(id);
    }

    @Override
    public boolean delete(Status status) {
        return statusRepository.findById(status.getId()).isEmpty();
    }

}
