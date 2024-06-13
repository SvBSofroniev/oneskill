package com.platform.OneSkill.util;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class TimeUtil {

    public static String getCurrentZonedDateTime() {
        Instant nowUtc = Instant.now();
        ZoneId zoneId = ZoneId.systemDefault();
        return ZonedDateTime.ofInstant(nowUtc, zoneId).toString();
    }
}
