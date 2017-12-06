package com.noriginmedia.epg.presentation.epg;


import com.noriginmedia.epg.data.network.models.Channel;

import java.util.List;

public interface EpgScreen {

    void setDates(List<Long> dates);

    int getSelectedDatePosition();
    void setSelectedDate(long timestamp);

    void setEpg(List<Channel> schedule);
}
