package com.noriginmedia.epg.di;

import com.noriginmedia.epg.data.network.NetworkDataSource;
import com.noriginmedia.epg.presentation.MainActivity;

import javax.inject.Singleton;

import dagger.Component;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

@Singleton
@Component(modules = {AppModule.class, NetworkModule.class,})
public interface AppComponent {

    NetworkDataSource networkDataSource();

    Subject<Boolean> networkStatePublisher();

    //FIXME not right place for registration MainActivity
    void inject(MainActivity activity);
}
