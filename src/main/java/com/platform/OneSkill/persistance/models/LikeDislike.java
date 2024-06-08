package com.platform.OneSkill.persistance.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Date;

@Getter
@Setter
@EqualsAndHashCode
@RequiredArgsConstructor
@Document(collection = "LikesDislikes")
public class LikeDislike {

    @MongoId
    private String likeId;

    @Indexed
    private String videoId;

    @Indexed
    private String userId;

    private String type; // "like" or "dislike"
    private Date timestamp;

}