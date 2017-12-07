package com.noriginmedia.epg.presentation.epg;


import com.noriginmedia.epg.data.network.models.Channel;
import com.noriginmedia.epg.presentation.BaseScreen;

import java.util.List;

public interface EpgScreen extends BaseScreen {

    void setDates(List<Long> dates);

    int getSelectedDatePosition();

    void setSelectedDate(long timestamp);

    void setEpg(List<Channel> schedule);
}
