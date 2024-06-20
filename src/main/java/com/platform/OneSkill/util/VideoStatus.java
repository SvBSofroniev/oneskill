package com.platform.OneSkill.util;

import lombok.Getter;

@Getter
public enum VideoStatus {
    ACTIVE("active"),
    SUSPENDED("suspended");

    private final String value;

    VideoStatus(String value){
        this.value = value;
    }
}
