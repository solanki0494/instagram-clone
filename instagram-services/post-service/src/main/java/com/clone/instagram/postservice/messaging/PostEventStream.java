package com.clone.instagram.postservice.messaging;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface PostEventStream {

    String OUTPUT = "postChanged";

    @Output(OUTPUT)
    MessageChannel sendPost();
}
