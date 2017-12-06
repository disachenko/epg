package com.noriginmedia.epg.presentation.epg.view;


import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;

import com.noriginmedia.epg.common.DateUtils;

import java.util.ArrayList;
import java.util.List;

public class EpgScrollListener extends RecyclerView.OnScrollListener {

    private List<RecyclerView> extraScrollObservers;
    private List<OnEpgScrollEventListener> onEpgScrollEventListeners;

    private RecyclerView.LayoutManager layoutManager;
    @IdRes
    private int recyclerViewId;

    private EpgView epgView;

    private int overallXScroll = 0;
    private int timeLineHourWidth = 0;

    EpgScrollListener(EpgView epgView, int timeLineHourWidth) {
        this.epgView = epgView;
        this.timeLineHourWidth = timeLineHourWidth;
        extraScrollObservers = new ArrayList<>();
        onEpgScrollEventListeners = new ArrayList<>();
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        doSynchronousScroll(recyclerView, dx, dy);
    }

    void setLayoutManager(RecyclerView.LayoutManager layoutManager, @IdRes int recyclerViewId) {
        this.layoutManager = layoutManager;
        this.recyclerViewId = recyclerViewId;
    }

    void addScrollObserver(RecyclerView recyclerView) {
        extraScrollObservers.add(recyclerView);
    }

    void addOnEpgScrollEventListener(OnEpgScrollEventListener listener) {
        onEpgScrollEventListeners.add(listener);
    }

    int getOverallXScroll() {
        return overallXScroll;
    }

    void setOverallXScroll(int overallXScroll) {
        int dx = overallXScroll - this.overallXScroll;
        doSynchronousScroll(null, dx, 0);
        this.overallXScroll = overallXScroll;
    }

    private void doSynchronousScroll(RecyclerView recyclerView, int dx, int dy) {
        scrollItemsFromLayoutManager(recyclerView, dx, dy);
        scrollObservers(recyclerView, dx, dy);
        notifyListeners();
    }

    private void scrollItemsFromLayoutManager(RecyclerView recyclerView, int dx, int dy) {
        if (layoutManager == null) {
            return;
        }

        for (int i = 0; i < layoutManager.getChildCount(); i++) {
            RecyclerView child = layoutManager.getChildAt(i).findViewById(recyclerViewId);
            if (child == recyclerView) {
                overallXScroll += dx;
            } else {
                scroll(child, dx, dy);
            }
        }
    }

    private void scrollObservers(RecyclerView recyclerView, int dx, int dy) {
        for (RecyclerView observer : extraScrollObservers) {
            if (observer == recyclerView) {
                overallXScroll += dx;
            } else {
                scroll(observer, dx, dy);
            }
        }
    }

    private void notifyListeners() {
        long timestamp = (long) ((double) overallXScroll / timeLineHourWidth * DateUtils.HOUR) + epgView.getStartTime();
        for (OnEpgScrollEventListener listener : onEpgScrollEventListeners) {
            listener.onScroll(timestamp);
        }
    }

    private void scroll(RecyclerView recyclerView, int dx, int dy) {
        recyclerView.removeOnScrollListener(this);
        recyclerView.scrollBy(dx, dy);
        recyclerView.addOnScrollListener(this);
    }

    public interface OnEpgScrollEventListener {
        void onScroll(long timestamp);
    }
}