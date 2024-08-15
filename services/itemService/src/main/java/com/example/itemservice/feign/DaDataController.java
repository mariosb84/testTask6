package com.example.itemservice.feign;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(value = "/phone")
@RequiredArgsConstructor
public class DaDataController {

    private final DaDataApiClient apiClient;
    private final GetPhoneData getPhoneData;

    @PreAuthorize("hasRole('USER') || hasRole('OPERATOR') || hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<List<PhoneDataDto>> checkPhoneData(@RequestBody PhoneSource phoneSource) {
        var checkResult = getPhoneData.getStandPhoneData(
                apiClient.readPhoneDataByPhoneNumber(phoneSource));
        if (checkResult != null) {
            return ResponseEntity.ok(checkResult);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                "номер телефона не найден!");
    }

}
