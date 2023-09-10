package com.clone.instagram.postservice.service;

import com.clone.instagram.postservice.messaging.PostEventSender;
import com.clone.instagram.postservice.model.Post;
import com.clone.instagram.postservice.repository.PostRepository;
import com.clone.instagram.postservice.web.rest.exception.NotAllowedException;
import com.clone.instagram.postservice.web.rest.exception.ResourceNotFoundException;
import com.clone.instagram.postservice.web.rest.payload.PostRequestVM;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostEventSender postEventSender;

    public Post createPost(PostRequestVM postRequestVM) {
        log.info("creating post image url {}", postRequestVM.getImageUrl());

        Post post = new Post(postRequestVM.getImageUrl(), postRequestVM.getCaption());

        post = postRepository.save(post);
        postEventSender.sendPostCreated(post);

        log.info("post {} is saved successfully for user {}",
                post.getId(), post.getUsername());

        return post;
    }

    public void deletePost(String postId, String username) {
        log.info("deleting post {}", postId);

        postRepository
                .findById(postId)
                .map(post -> {
                    if(!post.getUsername().equals(username)) {
                        log.warn("user {} is not allowed to delete post id {}", username, postId);
                        throw new NotAllowedException(username, "post id " + postId, "delete");
                    }

                    postRepository.delete(post);
                    postEventSender.sendPostDeleted(post);
                    return post;
                })
                .orElseThrow(() -> {
                    log.warn("post not found id {}", postId);
                    return new ResourceNotFoundException(postId);
                });
    }

    public List<Post> postsByUsername(String username) {
        return postRepository.findByUsernameOrderByCreatedAtDesc(username);
    }

    public List<Post> postsByIdIn(List<String> ids) {
        return postRepository.findByIdInOrderByCreatedAtDesc(ids);
    }
}
