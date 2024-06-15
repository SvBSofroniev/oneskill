package com.platform.OneSkill.util;


public enum RolesEnum {
    DEV("dev"),
    USER("user");

    private final String value;

    RolesEnum(String value){
        this.value = value;
    }

    public String getValue(){
        return value;
    }
}
