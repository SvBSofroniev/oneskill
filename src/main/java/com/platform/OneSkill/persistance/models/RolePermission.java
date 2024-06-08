package com.platform.OneSkill.persistance.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@RequiredArgsConstructor
@Document(collection = "RolesPermissions")
public class RolePermission {

    @MongoId
    private String roleId;

    @Indexed(unique = true)
    private String roleName;

    private List<String> permissions;
}