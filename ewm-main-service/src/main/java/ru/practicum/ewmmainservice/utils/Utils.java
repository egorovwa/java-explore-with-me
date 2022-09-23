package ru.practicum.ewmmainservice.utils;

import java.time.format.DateTimeFormatter;

public class Utils {
    public static Long HOUR = 60L * 60;

    public static DateTimeFormatter getDateTimeFormater() {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }
}
