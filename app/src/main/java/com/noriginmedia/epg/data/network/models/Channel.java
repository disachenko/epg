package com.noriginmedia.epg.data.network.models;


import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import java.util.ArrayList;
import java.util.List;

@JsonObject
public class Channel {

    @JsonField(name = "id")
    private String id;

    @JsonField(name = "title")
    private String title;

    @JsonField(name = "images")
    private ChannelImages images;

    @JsonField(name = "schedules")
    private List<Schedule> schedules = new ArrayList<>();

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

    public ChannelImages getImages() {
        return images;
    }

    public void setImages(ChannelImages images) {
        this.images = images;
    }

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }
}
