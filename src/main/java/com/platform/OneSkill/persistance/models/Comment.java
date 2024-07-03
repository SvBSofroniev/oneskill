package com.platform.OneSkill.persistance.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Date;

@Getter
@Setter
@EqualsAndHashCode
@RequiredArgsConstructor
@Document(collection = "Comments")
public class Comment {

    @Id
    private String ID;

    @Field("video_id")
    private String videoId;

    private String username;

    private String content;

    private String timestamp;
}
