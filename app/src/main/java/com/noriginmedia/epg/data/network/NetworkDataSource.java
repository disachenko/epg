package com.noriginmedia.epg.data.network;


import android.content.Context;

import com.noriginmedia.epg.common.AndroidUtils;
import com.noriginmedia.epg.data.network.adapter.ScheduleApiAdapter;
import com.noriginmedia.epg.data.network.models.NoNetworkException;
import com.noriginmedia.epg.data.network.models.response.ChannelSchedulesResponse;

import io.reactivex.Observable;
import retrofit2.Retrofit;

public class NetworkDataSource implements ScheduleApiAdapter {

    private ScheduleApiAdapter scheduleApiAdapter;

    private Context appContext;

    /**
     * @param context  for checking network state
     * @param retrofit for network requests
     */
    public NetworkDataSource(Context context, Retrofit retrofit) {
        this.appContext = context;
        scheduleApiAdapter = retrofit.create(ScheduleApiAdapter.class);
    }

    @Override
    public Observable<ChannelSchedulesResponse> getChannelSchedule() {
        return call(scheduleApiAdapter.getChannelSchedule());
    }

    public boolean hasNetwork() {
        return AndroidUtils.hasNetworkConnection(appContext);
    }

    private <T> Observable<T> call(Observable<T> apiMethod) {
        return Observable.fromCallable(() -> hasNetwork())
                .flatMap(hasNetwork -> hasNetwork ? apiMethod : Observable.error(new NoNetworkException()));
    }
}
