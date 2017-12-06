package com.noriginmedia.epg.data.network.converters;


import com.bluelinelabs.logansquare.typeconverters.StringBasedTypeConverter;
import com.noriginmedia.epg.common.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TimestampConverter extends StringBasedTypeConverter<Long> {

    @Override
    public Long getFromString(String string) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(DateUtils.DATE_TIME_PATTERN_SERVER, Locale.getDefault());
            Date parsedDate = dateFormat.parse(string);
            return parsedDate.getTime();
        } catch (ParseException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public String convertToString(Long timestamp) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DateUtils.DATE_TIME_PATTERN_SERVER, Locale.getDefault());
        Date date = new Date(timestamp);
        return dateFormat.format(date);
    }
}
