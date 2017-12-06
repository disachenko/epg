package com.noriginmedia.epg.presentation;


import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class LocalSchedulers {

    public static Scheduler networking() {
        return Schedulers.io();
    }

    public static Scheduler uiThread() {
        return AndroidSchedulers.mainThread();
    }
}
