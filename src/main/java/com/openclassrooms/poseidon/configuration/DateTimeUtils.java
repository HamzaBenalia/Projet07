package com.openclassrooms.poseidon.configuration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtils {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

    public static LocalDateTime parseLocalDateTime(String dateTimeString) {
        return LocalDateTime.parse(dateTimeString, formatter);
    }

    public static String formatLocalDateTime(LocalDateTime localDateTime) {
        return localDateTime.format(formatter);
    }
}

