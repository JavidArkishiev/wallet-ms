package com.example.walletms.client;

import com.example.walletms.dto.response.BaseResponse;
import com.example.walletms.dto.response.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "auth-wallet-ms", url = "https://auth-wallet-ms-6b5e5d2763f7.herokuapp.com", configuration = FeignClientInterceptor.class)
public interface UserClient {

    @GetMapping("/users/profile/{phoneNumber}")
    BaseResponse<UserResponse> getProfileByPhoneNumber(@PathVariable String phoneNumber);


}
