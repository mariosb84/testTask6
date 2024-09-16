package com.example.itemservice.feign;

import com.example.itemservice.feign.config.CustomFeignClientConfiguration;
import com.example.itemservice.feign.domain.model.PhoneData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(value = "${app.feign.config.name}", url = "${app.feign.config.url}",
        configuration = CustomFeignClientConfiguration.class)
public interface DaDataApiClient {
    @RequestMapping(method = RequestMethod.POST, value = "/phone")
    List<PhoneData> readPhoneDataByPhoneNumber(@RequestBody PhoneSource phoneSource);

}
