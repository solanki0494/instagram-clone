package com.clone.instagram.feedservice.service;

import com.clone.instagram.feedservice.entity.UserFeed;
import com.clone.instagram.feedservice.exception.ResourceNotFoundException;
import com.clone.instagram.feedservice.model.Post;
import com.clone.instagram.feedservice.repository.FeedRepository;
import com.clone.instagram.feedservice.util.AppConstants;
import com.clone.instagram.feedservice.web.rest.payload.SlicedResult;
import com.datastax.oss.driver.api.core.cql.PagingState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.query.CassandraPageRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.toList;


@Service
@Slf4j
public class FeedService {

    @Autowired private FeedRepository feedRepository;
    @Autowired private PostService postService;
    @Autowired private AuthService authService;

    public SlicedResult<Post> getUserFeed(String username, Optional<String> pagingState) {
        log.info("getting feed for user {} isFirstPage {}", username, pagingState.isEmpty());

        CassandraPageRequest request = pagingState
                .map(pState -> CassandraPageRequest.of(PageRequest.of(0, AppConstants.PAGE_SIZE), PagingState.fromString(pState).getRawPagingState()))
                .orElse(CassandraPageRequest.first(AppConstants.PAGE_SIZE));

        Slice<UserFeed> page = feedRepository.findByUsername(username, request);

        if(page.isEmpty()) {
            throw new ResourceNotFoundException(String.format("Feed not found for user %s", username));
        }

        String pageState = null;

        if(!page.isLast()) {
           pageState = ((CassandraPageRequest)page.getPageable()).getPagingState().toString();
        }

        List<Post> posts = getPosts(page);

        return SlicedResult
                .<Post>builder()
                .content(posts)
                .isLast(page.isLast())
                .pagingState(pageState)
                .build();
    }

    private List<Post> getPosts(Slice<UserFeed> page) {
        List<String> postIds = page.stream()
                .map(feed -> feed.getPostId())
                .collect(toList());

        List<Post> posts = postService.findPostsIn(postIds);

        List<String> usernames = posts.stream()
                .map(Post::getUsername)
                .distinct()
                .collect(toList());

        Map<String, String> usersProfilePics =
                authService.usersProfilePic(new ArrayList<>(usernames));

        posts.forEach(post -> post.setUserProfilePic(
                usersProfilePics.get(post.getUsername())));

        return posts;
    }
}
