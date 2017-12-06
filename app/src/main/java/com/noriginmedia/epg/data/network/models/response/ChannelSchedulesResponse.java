package com.noriginmedia.epg.data.network.models.response;


import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.noriginmedia.epg.data.network.models.Channel;

import java.util.List;

@JsonObject
public class ChannelSchedulesResponse {

    @JsonField(name = "channels")
    private List<Channel> channels;

    private long startDate;
    private long endDate;

    public List<Channel> getChannels() {
        return channels;
    }

    public void setChannels(List<Channel> channels) {
        this.channels = channels;
    }

    public long getStartDate() {
        return startDate;
    }

    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }

    public long getEndDate() {
        return endDate;
    }

    public void setEndDate(long endDate) {
        this.endDate = endDate;
    }
}
