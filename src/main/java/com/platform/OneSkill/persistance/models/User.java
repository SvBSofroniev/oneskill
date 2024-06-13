package com.platform.OneSkill.persistance.models;

import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@RequiredArgsConstructor
@Document(collection = "Users")
public class User {

    private String username;

    @Indexed(unique = true)
    private String email;

    private String firstname;
    private String lastname;
    private String password;
    private List<String> roles;
    @Field("created_at")
    private String createdAt;
    @Field("updated_at")
    private String  updatedAt;
}
