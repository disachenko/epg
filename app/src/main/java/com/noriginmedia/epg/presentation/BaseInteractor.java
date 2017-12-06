package com.noriginmedia.epg.presentation;


import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.SingleTransformer;
import io.reactivex.subjects.PublishSubject;

public class BaseInteractor {

    private PublishSubject<Boolean> lifecycleSubject = PublishSubject.create();

    protected <T> SingleTransformer<T, T> baseCall() {
        return new BaseCallTransformer<>();
    }

    public void dropSubscriptions() {
        lifecycleSubject.onNext(true);
    }

    private final class BaseCallTransformer<T> implements SingleTransformer<T, T> {

        @Override
        public SingleSource<T> apply(Single<T> upstream) {
            return upstream.takeUntil(Single.fromObservable(lifecycleSubject))
                    .subscribeOn(LocalSchedulers.networking())
                    .observeOn(LocalSchedulers.uiThread());
        }
    }
}
