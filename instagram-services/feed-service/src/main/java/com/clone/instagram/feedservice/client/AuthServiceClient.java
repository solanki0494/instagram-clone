package com.clone.instagram.feedservice.client;

import com.clone.instagram.feedservice.web.rest.payload.UserSummary;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;


@FeignClient(name = "AUTH-SERVICE")
public interface AuthServiceClient {

    @RequestMapping(method = RequestMethod.POST, value = "/users/summary/in")
    ResponseEntity<List<UserSummary>> findByUsernameIn(@RequestBody List<String> usernames);
}
