package ru.practicum.ewmstatscontract.utils;

import java.time.format.DateTimeFormatter;

public class Utils {
    public static final Long HOUR = 60L * 60;

    public static DateTimeFormatter getDateTimeFormatter() {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }
}
