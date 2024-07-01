package com.platform.OneSkill.util;

import lombok.Getter;

@Getter
public enum RolesEnum {
    DEV("dev"),
    USER("user"),
    LECTOR("lector"),
    ADMIN("admin");

    private final String value;

    RolesEnum(String value){
        this.value = value;
    }

    public static boolean isValidRole(String role) {
        for (RolesEnum r : RolesEnum.values()) {
            if (r.getValue().equalsIgnoreCase(role)) {
                return true;
            }
        }
        return false;
    }

    public static RolesEnum fromString(String role) {
        for (RolesEnum r : RolesEnum.values()) {
            if (r.getValue().equalsIgnoreCase(role)) {
                return r;
            }
        }
        return null;
    }
}
