package com.example.itemservice.repository;


import com.example.itemservice.domain.dto.JwtAuthenticationResponse;
import lombok.NonNull;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TokenBlackListRepository extends CrudRepository<JwtAuthenticationResponse, Long> {

    public @NonNull List<JwtAuthenticationResponse> findAll();


}
