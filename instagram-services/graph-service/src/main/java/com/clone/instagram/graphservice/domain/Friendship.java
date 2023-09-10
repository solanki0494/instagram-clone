package com.clone.instagram.graphservice.domain;


import lombok.Builder;
import org.springframework.data.neo4j.core.schema.RelationshipId;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;


@RelationshipProperties
@Builder
public class Friendship {

    @RelationshipId
    private Long id;

//    @TargetNode
//    private User startNode;

    @TargetNode
    private User endNode;
}
