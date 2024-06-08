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
@Document(collection = "videos")
public class Video {

    @MongoId
    private String videoId;

    @Indexed
    private String userId;

    private String title;
    private String description;
    private byte[] videoData;
    private byte[] thumbnailData;
    private Date uploadDate;
    private String status;
    private int views;
    private int likes;
    private int dislikes;
    private int sharedCount;

}
