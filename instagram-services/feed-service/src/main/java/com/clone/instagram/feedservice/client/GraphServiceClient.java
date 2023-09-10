package com.clone.instagram.feedservice.client;

import com.clone.instagram.feedservice.model.User;
import com.clone.instagram.feedservice.web.rest.payload.PagedResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "GRAPH-SERVICE")
public interface GraphServiceClient {

    @RequestMapping(method = RequestMethod.GET, value = "/users/paginated/{username}/followers")
    ResponseEntity<PagedResult<User>> findFollowers(
            @PathVariable("username") String username,
            @RequestParam("page") int page,
            @RequestParam("size") int size);
}
