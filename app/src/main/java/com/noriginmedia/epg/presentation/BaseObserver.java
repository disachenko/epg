package com.noriginmedia.epg.presentation;


import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public abstract class BaseObserver<R, V extends BaseScreen> implements Observer<R> {

    protected Disposable disposable;
    protected V view;

    public BaseObserver(V view) {
        this.view = view;
    }

    @Override
    public void onSubscribe(Disposable d) {
        disposable = d;
    }

    public void onLoadingStart() {
        view.showProgress();
    }

    @Override
    public void onComplete() {
        view.hideProgress();
    }

    @Override
    public void onError(Throwable e) {
        view.hideProgress();
    }

    public void showNoNetwork() {
        view.showNoNetwork();
    }
}
