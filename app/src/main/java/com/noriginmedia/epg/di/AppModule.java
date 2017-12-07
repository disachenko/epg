package com.noriginmedia.epg.di;

import android.content.Context;

import com.noriginmedia.epg.App;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private App app;

    public AppModule(App app) {
        this.app = app;
    }

    @Singleton
    @Provides
    Context applicationContext() {
        return app;
    }
}
