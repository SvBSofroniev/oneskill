package com.platform.OneSkill.persistance.models;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@EqualsAndHashCode
@RequiredArgsConstructor
@Document(collection = "Videos")
public class Video {

    @Id
    private String id;

    @Indexed(unique = true)
    private String username;

    @Indexed(unique = true)
    private String title;

    private String description;

    @Field("video_id")
    private ObjectId videoId;

    @Field("thumbnail_id")
    private ObjectId thumbnailId;

    @Field("upload_date")
    private String uploadDate;

    private String status;

    private int views;

    private int likes;

    private int dislikes;

    @Field("shared_count")
    private int sharedCount;
}