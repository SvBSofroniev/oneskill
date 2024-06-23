package com.platform.OneSkill.service;

import com.platform.OneSkill.dto.VideoIdDTO;
import com.platform.OneSkill.dto.VideoInfoResponseDTO;
import com.platform.OneSkill.dto.VideoUploadDTO;
import com.platform.OneSkill.dto.VideoResponseDTO;
import java.io.IOException;
import java.util.List;


public interface VideoService {
    boolean uploadVideo(String username, VideoUploadDTO videoUploadDTO) throws IOException;

    VideoResponseDTO getVideo(String id) throws IOException;

    List<VideoInfoResponseDTO> getVideosInfoData();

    List<VideoIdDTO> getEnrolledVideos(String username);

    Boolean enrollToVideo(String username, String videoId);
}
