package com.platform.OneSkill.persistance.models;

import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Date;

@Getter
@Setter
@EqualsAndHashCode
@RequiredArgsConstructor
@Document(collection = "users")
public class User {

    @MongoId
    private String userId;

    @Indexed(unique = true)
    private String email;

    private String username;
    private String password;
    private String role;
    private Date createdAt;
    private Date updatedAt;
}
