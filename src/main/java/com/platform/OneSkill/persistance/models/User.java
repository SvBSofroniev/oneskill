package com.platform.OneSkill.persistance.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode
@RequiredArgsConstructor
@Document(collection = "Users")
public class User {

    @Id
    private String id;

    private String username;

    @Indexed(unique = true)
    private String email;

    private String firstname;
    private String lastname;
    private String password;
    private Set<String> roles;
    @Field("created_at")
    private String createdAt;
    @Field("updated_at")
    private String updatedAt;
}

