package com.clone.instagram.graphservice.web.rest.vm;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse {

    private Boolean success;
    private String message;
}