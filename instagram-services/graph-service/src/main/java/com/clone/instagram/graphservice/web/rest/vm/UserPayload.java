package com.clone.instagram.graphservice.web.rest.vm;


import lombok.Data;

@Data
public class UserPayload {

    private String id;
    private String username;
    private String name;
    private String profilePicture;
}
