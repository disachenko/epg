package com.noriginmedia.epg.presentation;


import com.noriginmedia.epg.data.network.models.NoNetworkException;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

public abstract class BaseObserver<R, V extends BaseScreen> implements SingleObserver<R> {

    protected Disposable disposable;
    protected V view;

    public BaseObserver(V view) {
        this.view = view;
    }

    @Override
    public void onSubscribe(Disposable d) {
        disposable = d;
    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof NoNetworkException) {
            view.showNoNetwork();
        } else {
            view.hideProgress();
        }
    }
}
