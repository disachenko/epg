package com.noriginmedia.epg.data.network.adapter;

import com.noriginmedia.epg.data.network.models.response.ChannelSchedulesResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface ScheduleApiAdapter {

    @GET("/epg")
    @Headers("Content-type: application/json; charset=UTF-8")
    Observable<ChannelSchedulesResponse> getChannelSchedule();
}
