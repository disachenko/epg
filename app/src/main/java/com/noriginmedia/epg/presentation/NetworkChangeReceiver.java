package com.noriginmedia.epg.presentation;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.noriginmedia.epg.common.AndroidUtils;

import javax.inject.Inject;

import io.reactivex.subjects.Subject;

public class NetworkChangeReceiver extends BroadcastReceiver {

    private final Subject<Boolean> networkStatePublisher;

    @Inject
    NetworkChangeReceiver(Subject<Boolean> networkStatePublisher) {
        this.networkStatePublisher = networkStatePublisher;
    }

    @Override
    public void onReceive(final Context context, final Intent intent) {
        if (AndroidUtils.hasNetworkConnection(context)) {
            networkStatePublisher.onNext(true);
        }
    }
}
