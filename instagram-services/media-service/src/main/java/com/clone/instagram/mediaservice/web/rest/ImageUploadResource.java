package com.clone.instagram.mediaservice.web.rest;


import com.clone.instagram.mediaservice.model.ImageMetadata;
import com.clone.instagram.mediaservice.service.ImageService;
import com.clone.instagram.mediaservice.web.rest.payload.UploadFileResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@Slf4j
@RestController("api/images")
@PreAuthorize("hasRole('USER')")
public class ImageUploadResource {

    @Autowired
    private ImageService imageService;

    @PostMapping
    public UploadFileResponse uploadFile(@RequestParam("image") MultipartFile file,
                                         @AuthenticationPrincipal Principal principal) {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        log.info("received a request to upload file {} for user {}", filename, principal.getName());

        ImageMetadata metadata = imageService.upload(file, principal.getName());

        return new UploadFileResponse(metadata.getFilename(), metadata.getUri(),
                metadata.getFileType());
    }

}
