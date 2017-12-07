package com.noriginmedia.epg.common.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.noriginmedia.epg.R;

/**
 * View for showing progress bar above container and intercepting touch events.
 */
public class ProgressPanel extends FrameLayout {

    private View progressView;

    public ProgressPanel(Context context) {
        super(context);
        init();
    }

    public ProgressPanel(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        progressView = LayoutInflater.from(getContext()).inflate(R.layout.view_progress_panel, null);
        //disable all clicks
        progressView.setOnClickListener(v -> {
        });
    }

    public final void showProgress() {
        if (indexOfChild(progressView) < 0) {
            addView(progressView);
        }
        progressView.setVisibility(VISIBLE);
    }

    public final void hideProgress() {
        progressView.setVisibility(GONE);
    }
}