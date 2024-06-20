package com.platform.OneSkill.service;

import com.platform.OneSkill.dto.VideoDTO;
import com.platform.OneSkill.dto.VideoResponseDTO;
import java.io.IOException;



public interface VideoService {
    boolean uploadVideo(VideoDTO videoDTO) throws IOException;

    VideoResponseDTO getVideo(String id) throws IOException;
}
