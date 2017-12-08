package com.noriginmedia.epg.presentation;


import android.content.Context;

public interface BaseScreen {

    Context getContext();

    void showProgress();

    void hideProgress();

    void showNoNetwork();
}
