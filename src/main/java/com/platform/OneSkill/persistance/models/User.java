package com.platform.OneSkill.persistance.models;

import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;

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
    private Set<String> roles;
    @Field("created_at")
    private String createdAt;
    @Field("updated_at")
    private String  updatedAt;
}
