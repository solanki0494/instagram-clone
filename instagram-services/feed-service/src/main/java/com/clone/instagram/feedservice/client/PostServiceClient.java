package com.clone.instagram.feedservice.client;

import com.clone.instagram.feedservice.model.Post;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;


@FeignClient(name = "POST-SERVICE")
public interface PostServiceClient {

    @RequestMapping(method = RequestMethod.POST, value = "/posts/in")
    ResponseEntity<List<Post>> findPostsByIdIn(@RequestBody List<String> ids);

}
