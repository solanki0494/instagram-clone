package com.clone.instagram.postservice.web.rest.payload;

import lombok.Data;

@Data
public class PostRequestVM {

    private String imageUrl;
    private String caption;
}
