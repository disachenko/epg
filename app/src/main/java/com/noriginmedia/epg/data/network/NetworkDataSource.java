package com.noriginmedia.epg.data.network;


import com.noriginmedia.epg.data.network.adapter.ScheduleApiAdapter;
import com.noriginmedia.epg.data.network.models.response.ChannelSchedulesResponse;

import io.reactivex.Single;
import retrofit2.Retrofit;

public class NetworkDataSource implements ScheduleApiAdapter {

    private ScheduleApiAdapter scheduleApiAdapter;

    public NetworkDataSource(Retrofit retrofit) {
        scheduleApiAdapter = retrofit.create(ScheduleApiAdapter.class);
    }

    @Override
    public Single<ChannelSchedulesResponse> getChannelSchedule() {
        return scheduleApiAdapter.getChannelSchedule();
    }
}
