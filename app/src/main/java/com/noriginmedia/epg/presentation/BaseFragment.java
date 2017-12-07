package com.noriginmedia.epg.presentation;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.noriginmedia.epg.App;
import com.noriginmedia.epg.R;
import com.noriginmedia.epg.common.view.ProgressPanel;
import com.noriginmedia.epg.presentation.dagger.DaggerScreenComponent;
import com.noriginmedia.epg.presentation.dagger.ScreenComponent;

public abstract class BaseFragment extends Fragment implements BaseScreen {

    @Nullable
    private ProgressPanel progressPanel;

    /**
     * @return dagger component for dependency injection
     */
    protected ScreenComponent getScreenComponent() {
        return DaggerScreenComponent.builder()
                .appComponent(((App) getContext().getApplicationContext()).getAppComponent())
                .build();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressPanel = view != null ? view.findViewById(R.id.root_progress_panel) : null;
    }

    @Override
    public void showProgress() {
        if (progressPanel != null) {
            progressPanel.showProgress();
        }
    }

    @Override
    public void hideProgress() {
        if (progressPanel != null) {
            progressPanel.hidePanel();
        }
    }

    @Override
    public void showNoNetwork() {
        if (progressPanel != null) {
            progressPanel.showNoNetwork();
        }
    }
}
