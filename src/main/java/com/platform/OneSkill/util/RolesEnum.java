package com.platform.OneSkill.util;

import lombok.Getter;

@Getter
public enum RolesEnum {
    DEV("dev"),
    USER("user");

    private final String value;

    RolesEnum(String value){
        this.value = value;
    }

}
