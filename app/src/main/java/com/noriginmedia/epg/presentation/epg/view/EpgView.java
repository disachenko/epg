package com.noriginmedia.epg.presentation.epg.view;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.noriginmedia.epg.R;
import com.noriginmedia.epg.common.DateUtils;
import com.noriginmedia.epg.common.view.recyclerview.DividerItemDecoration;
import com.noriginmedia.epg.data.network.models.Channel;
import com.noriginmedia.epg.data.network.models.Schedule;

import java.util.ArrayList;
import java.util.List;

public class EpgView extends LinearLayout {

    private RecyclerView timeline;
    private TimelineAdapter timelineAdapter;

    private RecyclerView epg;
    private EpgAdapter epgAdapter;

    private EpgScrollListener scrollListener;

    private int hourWidth;
    private int timelineHeight;
    private int programSize;

    public EpgView(Context context) {
        super(context);
        init(null);
    }

    public EpgView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public EpgView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public EpgView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        setOrientation(VERTICAL);
        applyAttributes(attrs);

        scrollListener = new EpgScrollListener(this, hourWidth);

        addView(timeline = createTimeLine());
        timeline.setAdapter(timelineAdapter = new TimelineAdapter(hourWidth, programSize));
        timeline.addOnScrollListener(scrollListener);
        scrollListener.addScrollObserver(timeline);

        addView(createDivider());

        addView(epg = createEpg());
        scrollListener.setLayoutManager(epg.getLayoutManager(), R.id.schedule);
        //TODO remove dependencies on Glide from EpgView
        epg.setAdapter(epgAdapter = new EpgAdapter(scrollListener, Glide.with(this), hourWidth));
    }

    private void applyAttributes(AttributeSet attrs) {
        if (attrs == null) {
            return;
        }

        //TODO implement parsing attributes

        Resources res = getResources();
        hourWidth = res.getDimensionPixelSize(R.dimen.epg_timeline_hour_width);
        programSize = res.getDimensionPixelSize(R.dimen.epg_program_size);
        timelineHeight = res.getDimensionPixelSize(R.dimen.list_item_height);
    }

    private RecyclerView createTimeLine() {
        RecyclerView timeLine = new RecyclerView(getContext());
        timeLine.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, timelineHeight));
        timeLine.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        return timeLine;
    }

    private View createDivider() {
        View divider = new View(getContext());
        int height = (int) getResources().getDimension(R.dimen.epg_divider_size);
        divider.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height));
        divider.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.divider_color));
        return divider;
    }

    private RecyclerView createEpg() {
        RecyclerView schedule = new RecyclerView(getContext());
        schedule.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        schedule.setLayoutManager(new LinearLayoutManager(getContext()));

        Drawable dividerDrawable = ContextCompat.getDrawable(getContext(), R.drawable.divider_decoration);
        schedule.addItemDecoration(new DividerItemDecoration(dividerDrawable));

        schedule.setHasFixedSize(true);

        return schedule;
    }

    public void setTime(long timestamp) {
        if (getStartTime() == -1) {
            return;
        }

        double dT = timestamp - getStartTime();
        dT = dT > 0 ? dT : 0;
        scrollListener.setOverallXScroll((int) ((dT / DateUtils.HOUR) * hourWidth));
    }

    /**
     * @return -1, if eph is empty
     */
    public long getStartTime() {
        return timelineAdapter.getItems().isEmpty() ? -1 : timelineAdapter.getItem(0);
    }

    public void setEpg(List<Channel> schedule) {
        timelineAdapter.setItems(getHours(schedule));
        epgAdapter.setItems(schedule);
    }

    private List<Long> getHours(List<Channel> schedule) {
        if (schedule.isEmpty()) {
            return new ArrayList<>();
        }

        List<Schedule> schedules = schedule.get(0).getSchedules();
        long start = schedules.get(0).getStart();
        long end = schedules.get(schedules.size() - 1).getEnd();

        List<Long> hours = DateUtils.split(start, end, DateUtils.HOUR);
        if (DateUtils.isSameHour(end, hours.get(hours.size() - 1))) {
            hours.remove(hours.size() - 1);
        }

        long lastHour = hours.get(hours.size() - 1);
        long hourOffset = (long) (((double) programSize / hourWidth) * DateUtils.HOUR);
        hours.add(lastHour + hourOffset);
        return hours;
    }

    public void addOnEpgScrollEventListener(EpgScrollListener.OnEpgScrollEventListener listener) {
        scrollListener.addOnEpgScrollEventListener(listener);
    }

}
