package com.platform.OneSkill.util.mapper;

import com.platform.OneSkill.dto.UserDTO;
import com.platform.OneSkill.persistance.models.User;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class UserMapper {

    public static UserDTO mapModelToRecord(User user){
        return new UserDTO(
                user.getFirstname(),
                user.getLastname(),
                user.getEmail(),
                user.getUsername(),
                user.getPassword(),
                user.getRoles(),
                formatMongoDate(user.getCreatedAt()),
                formatMongoDate(user.getUpdatedAt()));
    }

    public static String formatMongoDate(String mongoDate) {
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(mongoDate);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return zonedDateTime.withZoneSameInstant(ZoneId.systemDefault()).format(formatter);
    }
}
