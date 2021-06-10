package br.com.neves.shorturl.utils;

import java.time.LocalDateTime;

public class DateUtil {
// -------------------------- STATIC METHODS --------------------------

    public static LocalDateTime sumDaysInCurrentDate(Integer days) {
        return LocalDateTime.now().plusDays(days);
    }
}
