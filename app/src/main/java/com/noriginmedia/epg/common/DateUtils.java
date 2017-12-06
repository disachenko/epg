package com.noriginmedia.epg.common;


import android.content.Context;
import android.support.annotation.Nullable;

import com.noriginmedia.epg.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class DateUtils {

    public final static String DATE_TIME_PATTERN_SERVER = "yyyy-MM-dd'T'HH:mm:ssX";
    private final static String DAY_MONTH_PATTERN = "dd.MM";
    private final static String TIME_PATTERN = "HH:mm";

    public static final int HOUR = 60 * 60 * 1000;
    public static final int DAY = 24 * HOUR;


    public static boolean isSameHour(long firstTimeStamp, long secondTimeStamp) {
        if (!isSameDay(firstTimeStamp, secondTimeStamp)) {
            return false;
        }

        Calendar calendar = Calendar.getInstance();

        calendar.setTimeInMillis(firstTimeStamp);
        int firstHour = calendar.get(Calendar.HOUR_OF_DAY);

        calendar.setTimeInMillis(secondTimeStamp);
        int secondHour = calendar.get(Calendar.HOUR_OF_DAY);

        return firstHour == secondHour;
    }

    public static boolean isSameDay(long firstTimeStamp, long secondTimeStamp) {
        Calendar calendar = Calendar.getInstance();

        calendar.setTimeInMillis(firstTimeStamp);
        int firstYear = calendar.get(Calendar.YEAR);
        int firstDayOfYear = calendar.get(Calendar.DAY_OF_YEAR);

        calendar.setTimeInMillis(secondTimeStamp);
        int secondYear = calendar.get(Calendar.YEAR);
        int secondDayOfYear = calendar.get(Calendar.DAY_OF_YEAR);

        return (firstYear == secondYear) && (firstDayOfYear == secondDayOfYear);
    }

    public static long resetDay(long timestamp) {
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        calendar.setTimeInMillis(timestamp);

        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTimeInMillis();
    }

    public static long resetHour(long timestamp) {
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        calendar.setTimeInMillis(timestamp);

        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTimeInMillis();
    }

    public static List<Long> split(long start, long end, long shift) {
        long current = start;

        List<Long> result = new ArrayList<>();
        while (current <= end) {
            result.add(current);
            current += shift;
        }

        return result;
    }

    public static String getTimeRange(long start, long end) {
        return getLocalTimeString(start) + "-" + getLocalTimeString(end);
    }

    public static String getLocalDayMonthString(Long timestamp) {
        return getString(DAY_MONTH_PATTERN, timestamp);
    }

    public static String getLocalTimeString(Long timestamp) {
        return getString(TIME_PATTERN, timestamp);
    }

    private static String getString(String pattern, @Nullable Long timestamp) {
        return getString(pattern, timestamp != null ? new Date(timestamp) : null);
    }

    private static String getString(String pattern, @Nullable Date date) {
        if (date == null) {
            return "";
        }
        return new SimpleDateFormat(pattern, Locale.getDefault()).format(date);
    }

    public static String getWeekDayName(Context context, @Nullable Long timestamp) {
        if (timestamp == null) {
            return "";
        }

        String[] weekDayNames = context.getResources().getStringArray(R.array.week_days);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp);
        switch (calendar.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.MONDAY:
                return weekDayNames[0];
            case Calendar.TUESDAY:
                return weekDayNames[1];
            case Calendar.WEDNESDAY:
                return weekDayNames[2];
            case Calendar.THURSDAY:
                return weekDayNames[3];
            case Calendar.FRIDAY:
                return weekDayNames[4];
            case Calendar.SATURDAY:
                return weekDayNames[5];
            default:
                return weekDayNames[6];
        }
    }
}
