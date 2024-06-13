package com.platform.OneSkill.persistance.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Date;

@Getter
@Setter
@EqualsAndHashCode
@RequiredArgsConstructor
@Document(collection = "Videos")
public class Video {

    @Indexed
    private String username;

    private String title;
    private String description;

    @Field("video_data")
    private byte[] videoData;

    @Field("thumbnail_data")
    private byte[] thumbnailData;

    @Field("upload_date")
    private String uploadDate;
    private String status;
    private int views;
    private int likes;
    private int dislikes;

    @Field("shared_count")
    private int sharedCount;

}
