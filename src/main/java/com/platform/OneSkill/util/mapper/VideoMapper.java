package com.platform.OneSkill.util.mapper;

import com.platform.OneSkill.dto.ImageDTO;
import com.platform.OneSkill.dto.VideoInfoResponseDTO;
import com.platform.OneSkill.persistance.models.Video;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;

public class VideoMapper {

    private static final Logger log = LoggerFactory.getLogger(VideoMapper.class);

    public static List<VideoInfoResponseDTO> mapModelListToResponseInfoDTOList(
            List<Video> videoModels,
            Map<String, ImageDTO> videoImageMap){

       List<VideoInfoResponseDTO> mappedList = new ArrayList<>();
       videoModels.forEach(model -> {
                   try {
                       mappedList.add(
                               mapModelToResponseInfoDTO(model, videoImageMap.get(model.getTitle()))
                               );
                   } catch (IOException e) {
                       log.error("Error parsing thumbnail to base64");
                       throw new RuntimeException(e);
                   }
               }
       );
        return mappedList;
    }


    private static VideoInfoResponseDTO mapModelToResponseInfoDTO(Video video, ImageDTO imageDTO) throws IOException {
        return new VideoInfoResponseDTO(
                video.getUsername(),
                video.getVideoId().toString(),
                video.getTitle(),
                video.getDescription(),
                video.getStatus(),
                video.getUploadDate(),
                convertInputStreamToBase64(imageDTO.getPhotoStream()),
                video.getLikes(),
                video.getDislikes(),
                video.getViews()
        );
    }

    public static String convertInputStreamToBase64(InputStream inputStream) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[8192];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        byte[] bytes = outputStream.toByteArray();
        return Base64.getEncoder().encodeToString(bytes);
    }
}
