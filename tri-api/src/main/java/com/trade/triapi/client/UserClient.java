package com.trade.triapi.client;

import com.trade.triapi.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service", url = "${user.service.url:http://localhost:8072}")
public interface UserClient {

    @GetMapping("/users/{id}")
    UserDTO getUserById(@PathVariable("id") String id);
}