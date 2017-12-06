package com.noriginmedia.epg.di;

import com.noriginmedia.epg.data.network.NetworkDataSource;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {NetworkModule.class,})
public interface AppComponent {

    NetworkDataSource networkDataSource();
}
