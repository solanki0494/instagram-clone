package com.clone.instagram.feedservice.service;

import com.clone.instagram.feedservice.client.PostServiceClient;
import com.clone.instagram.feedservice.exception.UnableToGetPostsException;
import com.clone.instagram.feedservice.model.Post;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class PostService {

    @Autowired private PostServiceClient postServiceClient;

    public List<Post> findPostsIn(List<String> ids) {
        log.info("finding posts for ids {}", ids);

        ResponseEntity<List<Post>> response = postServiceClient.findPostsByIdIn(ids);

        if(response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            throw new UnableToGetPostsException(String.format("unable to get posts for ids", ids));
        }
    }
}
