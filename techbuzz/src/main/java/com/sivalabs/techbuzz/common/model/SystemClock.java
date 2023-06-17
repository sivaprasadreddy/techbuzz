package com.sivalabs.techbuzz.common.model;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class SystemClock {
    public static final ZoneId ZONE_ID = ZoneId.systemDefault();

    public static LocalDateTime dateTimeNow() {
        return LocalDateTime.now(ZONE_ID);
    }
}
