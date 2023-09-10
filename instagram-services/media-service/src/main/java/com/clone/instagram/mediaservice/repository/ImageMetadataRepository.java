package com.clone.instagram.mediaservice.repository;

import com.clone.instagram.mediaservice.model.ImageMetadata;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ImageMetadataRepository extends MongoRepository<ImageMetadata, String> {

}
