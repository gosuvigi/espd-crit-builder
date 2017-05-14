package com.vigi.edm;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by vigi on 5/14/2017.
 */
public final class LocalTimeAdapter {

    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm:ss");

    private LocalTimeAdapter() {

    }

    public static LocalTime unmarshal(String v) {
        if (v == null || v.trim().isEmpty()) {
            return null;
        }
        return LocalTime.parse(v, TIME_FORMAT);
    }

    public static String marshal(LocalTime v) {
        if (v == null) {
            return null;
        }
        return v.format(TIME_FORMAT);
    }
}