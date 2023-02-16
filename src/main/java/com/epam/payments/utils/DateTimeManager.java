package com.epam.payments.utils;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateTimeManager {
    public static Timestamp getCurrentTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }

    public static Date getBankCardExpirationDate(String expirationDateMonthAndYear) {
        LocalDate convertedDate = LocalDate.parse("01/" + expirationDateMonthAndYear, DateTimeFormatter.ofPattern("dd/MM/yy"));
        convertedDate = convertedDate.withDayOfMonth(convertedDate.getMonth().length(convertedDate.isLeapYear()));
        return Date.valueOf(convertedDate);
    }
}
