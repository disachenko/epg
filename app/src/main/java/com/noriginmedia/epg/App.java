package com.noriginmedia.epg;

import android.app.Application;

import com.noriginmedia.epg.di.AppComponent;
import com.noriginmedia.epg.di.DaggerAppComponent;
import com.noriginmedia.epg.di.NetworkModule;

public class App extends Application {

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = createAppComponent();
    }

    private AppComponent createAppComponent() {
        return DaggerAppComponent.builder()
                .networkModule(new NetworkModule())
                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }


}
