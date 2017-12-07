package com.noriginmedia.epg.data.network.models;


import android.support.annotation.Nullable;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.noriginmedia.epg.data.network.converters.TimestampConverter;

@JsonObject
public class Schedule {

    /**
     * null if there isn't any program on channel at current time
     */
    @Nullable
    @JsonField(name = "id")
    private String id;

    @JsonField(name = "title")
    private String title;

    @JsonField(name = "start", typeConverter = TimestampConverter.class)
    private long start;

    @JsonField(name = "end", typeConverter = TimestampConverter.class)
    private long end;

    public Schedule() {
    }

    public Schedule(String id, String title, long start, long end) {
        this.id = id;
        this.title = title;
        this.start = start;
        this.end = end;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }
}
