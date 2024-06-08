package com.platform.OneSkill.persistance.models;

import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.util.Date;

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
    private String role;

    @Field("created_at")
    private Date createdAt;
    @Field("updated_at")
    private Date updatedAt;
}
