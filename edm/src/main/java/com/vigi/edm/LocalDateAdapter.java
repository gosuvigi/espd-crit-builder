package com.vigi.edm;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Created by vigi on 5/14/2017.
 */
public final class LocalDateAdapter {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ISO_LOCAL_DATE;

    private LocalDateAdapter() {

    }

    public static LocalDate unmarshal(String v) {
        if (v == null || v.trim().isEmpty()) {
            return null;
        }
        return LocalDate.parse(v, DATE_FORMAT);
    }

    public static String marshal(LocalDate v) {
        if (v == null) {
            return null;
        }
        return v.format(DATE_FORMAT);
    }
}