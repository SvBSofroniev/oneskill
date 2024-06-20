package com.platform.OneSkill.persistance.models;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@EqualsAndHashCode
@RequiredArgsConstructor
@Document(collection = "Videos")
public class Video {

    @Id
    private ObjectId id;

    @Indexed(unique = true)
    private String username;

    @Indexed(unique = true)
    private String title;

    private String description;

    private ObjectId videoId;

    private ObjectId thumbnailId;

    private String uploadDate;

    private String status;

    private int views;

    private int likes;

    private int dislikes;

    private int sharedCount;
}