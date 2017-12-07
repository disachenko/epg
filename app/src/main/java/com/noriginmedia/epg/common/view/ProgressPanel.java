package com.noriginmedia.epg.common.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.noriginmedia.epg.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * View for showing progress bar above container and intercepting touch events.
 */
public class ProgressPanel extends FrameLayout {

    @BindView(R.id.container)
    View container;

    @BindView(R.id.progress)
    View progress;

    @BindView(R.id.no_network)
    View noNetwork;

    public ProgressPanel(Context context) {
        super(context);
        init();
    }

    public ProgressPanel(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_progress_panel, null);
        ButterKnife.bind(this, view);


        //disable all clicks
        container.setOnClickListener(v -> {
        });
    }

    public final void showProgress() {
        show(true, false);

    }

    public final void showNoNetwork() {
        show(false, true);
    }

    private void show(boolean isProgress, boolean isNoNetwork) {
        if (indexOfChild(container) < 0) {
            addView(container);
        }
        container.setVisibility(VISIBLE);
        progress.setVisibility(isProgress ? VISIBLE : GONE);
        noNetwork.setVisibility(isNoNetwork ? VISIBLE : GONE);
    }

    public final void hidePanel() {
        container.setVisibility(GONE);
    }
}