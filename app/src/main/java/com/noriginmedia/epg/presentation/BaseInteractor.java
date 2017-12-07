package com.noriginmedia.epg.presentation;


import com.noriginmedia.epg.data.network.NetworkDataSource;
import com.noriginmedia.epg.data.network.models.NoNetworkException;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

public class BaseInteractor {

    private PublishSubject<Boolean> lifecycleSubject = PublishSubject.create();

    protected final NetworkDataSource networkDataSource;
    private final Subject<Boolean> networkStatePublisher;

    @Inject
    public BaseInteractor(NetworkDataSource networkDataSource, Subject<Boolean> networkStatePublisher) {
        this.networkDataSource = networkDataSource;
        this.networkStatePublisher = networkStatePublisher;
    }

    public void dropSubscriptions() {
        lifecycleSubject.onNext(true);
    }

    protected <T> Observable<T> startLoading(Observable<T> call, BaseObserver<?, ?> observer) {
        return Observable.defer(() -> {
            observer.onLoadingStart();
            return call.subscribeOn(LocalSchedulers.networking());
        });
    }

    protected <T> ObservableTransformer<T, T> manageSubscription() {
        return new ManageSubscriptionTransformer<>();
    }

    private final class ManageSubscriptionTransformer<T> implements ObservableTransformer<T, T> {

        @Override
        public Observable<T> apply(Observable<T> upstream) {
            return upstream.takeUntil(lifecycleSubject);
        }
    }

    protected <T> ObservableTransformer<T, T> retryNoNetwork(BaseObserver<T, ?> observer) {
        return new RetryNoNetworkTransformer<>(observer);
    }

    private final class RetryNoNetworkTransformer<T> implements ObservableTransformer<T, T> {

        private BaseObserver<T, ?> observer;

        private RetryNoNetworkTransformer(BaseObserver<T, ?> observer) {
            this.observer = observer;
        }

        @Override
        public Observable<T> apply(Observable<T> upstream) {
            return upstream.observeOn(LocalSchedulers.uiThread())
                    .retryWhen(errors -> errors.flatMap(error -> {
                        if (error instanceof NoNetworkException || !networkDataSource.hasNetwork()) {
                            observer.showNoNetwork();
                            return networkStatePublisher;
                        }
//                     For anything else, don't retry
                        return Observable.<Boolean>error(error);
                    }));
        }
    }
}
