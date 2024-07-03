package com.platform.OneSkill.util;

import lombok.Getter;

@Getter

public enum InteractionEnum {
    LIKE("like"),
    DISLIKE("dislike"),
    NONE("none");

    private final String value;

    InteractionEnum(String value) {
        this.value = value;
    }

    public static boolean isValidRole(String role) {
        for (InteractionEnum i : InteractionEnum.values()) {
            if (i.getValue().equalsIgnoreCase(role)) {
                return true;
            }
        }
        return false;
    }

    public static InteractionEnum fromString(String role) {
        for (InteractionEnum i : InteractionEnum.values()) {
            if (i.getValue().equalsIgnoreCase(role)) {
                return i;
            }
        }
        return null;
    }
}
