package com.noriginmedia.epg.presentation;


import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

public abstract class BaseObserver<R> implements SingleObserver<R> {

    protected Disposable disposable;

    @Override
    public void onSubscribe(Disposable d) {
        disposable = d;
    }

    @Override
    public void onError(Throwable e) {
    }
}
