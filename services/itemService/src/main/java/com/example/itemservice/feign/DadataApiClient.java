package com.example.itemservice.feign;

import com.example.itemservice.feign.config.CustomFeignClientConfiguration;
import org.bouncycastle.util.encoders.UTF8;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "${app.feign.config.nameTwo}", url = "${app.feign.config.urlTwo}",
        configuration = CustomFeignClientConfiguration.class)
public interface DadataApiClient {

    /*@RequestMapping(method = RequestMethod.GET, value = "/phoneCheck/")
    PhoneData readPhoneDataByPhoneNumber(@RequestParam("source") String source);
*/

    @RequestMapping(method = RequestMethod.POST, value = "/phoneCheck/")
    PhoneData readPhoneDataByPhoneNumber(@RequestBody String source);


}
