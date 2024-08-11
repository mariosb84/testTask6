package com.example.itemservice.feign;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
/*import org.springframework.http.MediaType;*/
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/phoneCheck")
@RequiredArgsConstructor
public class DadataController {

    private final DadataApiClient apiClient;

    /*@GetMapping
    public ResponseEntity<?> checkPhoneData(@RequestParam(value = "sourcePhone", defaultValue = "",
            required = false) String sourcePhone) {
        var checkResult = apiClient.readPhoneDataByPhoneNumber(sourcePhone);
        if (checkResult != null) {
            return ResponseEntity.ok(checkResult);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                 "номер телефона не найден!");
    }*/

    @PostMapping
    public ResponseEntity<PhoneData> checkPhoneData(@RequestBody String sourcePhone) {
        var checkResult = apiClient.readPhoneDataByPhoneNumber(sourcePhone);
        if (checkResult != null) {
            return ResponseEntity.ok(checkResult);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                "номер телефона не найден!");
    }

}
