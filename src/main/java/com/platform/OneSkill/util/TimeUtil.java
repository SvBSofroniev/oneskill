package com.platform.OneSkill.util;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class TimeUtil {

    public static String getCurrentZonedDateTime() {
        Instant nowUtc = Instant.now();
        ZoneId zoneId = ZoneId.systemDefault();
        return ZonedDateTime.ofInstant(nowUtc, zoneId).toString();
    }

    public static String getFormattedTime(String timestamp){
        ZonedDateTime zdt = ZonedDateTime.parse(timestamp);

        // Define the desired format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy z");

        // Format the datetime in DD/MM/YY Sofia/Europe
        return zdt.format(formatter);
    }
}
