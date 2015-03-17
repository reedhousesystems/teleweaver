package com.reedhousesystems.services.core.health.messenger.util;

import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

/**
 * @author: Iain Porter
 */
public class DateUtil {

    private static final DateTimeFormatter ISO8061_FORMATTER = ISODateTimeFormat.dateTimeNoMillis();

    public static Date getDateFromIso8061DateString(String dateString) {
        return ISO8061_FORMATTER.parseDateTime(dateString).toDate();
    }

    public static String getCurrentDateAsIso8061String() {
        DateTime today = new DateTime();
        return ISO8061_FORMATTER.print(today);
    }

     public static String getDateDateAsIso8061String(DateTime date) {
        return ISO8061_FORMATTER.print(date);
    }
}
