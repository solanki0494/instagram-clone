package com.clone.instagram.graphservice.web.rest.vm;


import lombok.Data;

@Data
public class FollowRequest {

    UserPayload follower;
    UserPayload following;
}
