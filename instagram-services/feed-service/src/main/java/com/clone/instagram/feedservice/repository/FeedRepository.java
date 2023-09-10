package com.clone.instagram.feedservice.repository;

import com.clone.instagram.feedservice.entity.UserFeed;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;


public interface FeedRepository extends CassandraRepository<UserFeed, String> {

    Slice<UserFeed> findByUsername(String username, Pageable pageable);
}
