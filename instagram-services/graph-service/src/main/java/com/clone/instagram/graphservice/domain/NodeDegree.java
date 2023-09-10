package com.clone.instagram.graphservice.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NodeDegree {
    long outDegree;
    long inDegree;
}
