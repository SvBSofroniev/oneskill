package com.platform.OneSkill.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.InputStream;

@Getter
@Setter
public class ImageDTO {
    private InputStream photoStream;

    public ImageDTO(InputStream photoStream){
        this.photoStream = photoStream;
    }
}
