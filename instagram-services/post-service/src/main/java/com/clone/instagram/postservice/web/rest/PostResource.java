package com.clone.instagram.postservice.web.rest;

import com.clone.instagram.postservice.model.Post;
import com.clone.instagram.postservice.service.PostService;
import com.clone.instagram.postservice.web.rest.payload.ApiResponse;
import com.clone.instagram.postservice.web.rest.payload.PostRequestVM;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/posts")
public class PostResource {

    @Autowired
    private PostService postService;

    @PostMapping
    public ResponseEntity<?> createPost(@RequestBody PostRequestVM postRequestVM){
        log.info("received a request to create a post for image {}", postRequestVM.getImageUrl());

        Post post = postService.createPost(postRequestVM);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/posts/{id}")
                .buildAndExpand(post.getId()).toUri();

        return ResponseEntity
                .created(location)
                .body(new ApiResponse(true, "Post created successfully"));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePost(@PathVariable("id") String id, @AuthenticationPrincipal UserDetails user) {
        log.info("received a delete request for post id {} from user {}", id, user.getUsername());
        postService.deletePost(id, user.getUsername());
    }

    @GetMapping("me")
    public ResponseEntity<?> findCurrentUserPosts(@AuthenticationPrincipal(errorOnInvalidType = true) UserDetails userDetails) {
        log.info("retrieving posts for user {}", userDetails.getUsername());

        List<Post> posts = postService.postsByUsername(userDetails.getUsername());
        log.info("found {} posts for user {}", posts.size(), userDetails.getUsername());

        return ResponseEntity.ok(posts);
    }

    @GetMapping("{username}")
    public ResponseEntity<?> findUserPosts(@PathVariable("username") String username) {
        log.info("retrieving posts for user {}", username);

        List<Post> posts = postService.postsByUsername(username);
        log.info("found {} posts for user {}", posts.size(), username);

        return ResponseEntity.ok(posts);
    }

    @PostMapping("in")
    public ResponseEntity<?> findPostsByIdIn(@RequestBody List<String> ids) {
        log.info("retrieving posts for {} ids", ids.size());

        List<Post> posts = postService.postsByIdIn(ids);
        log.info("found {} posts", posts.size());

        return ResponseEntity.ok(posts);
    }
}
