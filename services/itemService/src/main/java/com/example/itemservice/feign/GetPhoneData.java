package com.example.itemservice.feign;

import com.example.itemservice.feign.domain.dto.PhoneDataDto;
import com.example.itemservice.feign.domain.model.PhoneData;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Service
public class GetPhoneData {

    public List<PhoneDataDto> getStandPhoneData(List<PhoneData> phoneDataList) {
        return phoneDataList.stream().map(p -> new PhoneDataDto(
                        p.getCity_code(), p.getCountry_code(), p.getPhone()
                )
        ).collect(Collectors.toList());
    }

}
