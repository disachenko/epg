package com.noriginmedia.epg.presentation.dagger;

import com.noriginmedia.epg.di.AppComponent;
import com.noriginmedia.epg.presentation.epg.EpgFragment;

import dagger.Component;

@PerScreen
@Component(dependencies = AppComponent.class)
public interface ScreenComponent {

    void inject(EpgFragment fragment);
}
