package com.noriginmedia.epg.presentation.epg;


import com.noriginmedia.epg.common.AndroidUtils;
import com.noriginmedia.epg.common.DateUtils;
import com.noriginmedia.epg.data.network.models.response.ChannelSchedulesResponse;
import com.noriginmedia.epg.presentation.BaseObserver;

import java.util.List;

import javax.inject.Inject;

public class EpgPresenter {

    private final EpgInteractor interactor;
    private EpgScreen view;

    private List<Long> dates;
    private long startDate;
    private long endDate;

    @Inject
    EpgPresenter(EpgInteractor interactor) {
        this.interactor = interactor;
    }

    void onViewCreated(EpgScreen view) {
        this.view = view;
        interactor.getChannelSchedules(new ScheduleObserver(view));
    }

    void onDestroyView() {
        interactor.dropSubscriptions();
        view = null;
    }

    void onEpgScrollEvent(long timestamp) {
        updateNowButtonParameters(timestamp);
        updateDateLine(timestamp);
    }

    private void updateNowButtonParameters(long leftTimestamp) {
        long currentTime = DateUtils.getCurrentTime();
        boolean isNowVisible = true;

        if ((startDate > currentTime) || endDate < (currentTime)) {
            isNowVisible = false;
        }

        long timePerProgramSize = (long) (((double) view.getProgramSize()) / view.getHourWidth() * DateUtils.HOUR);
        long timePerScreen = (long) (((double) AndroidUtils.getScreenWidthInPx(view.getContext()) / view.getHourWidth() * DateUtils.HOUR));
        long left = leftTimestamp - timePerProgramSize;
        long right = left + timePerScreen;
        if ((left < currentTime) && (currentTime < right)) {
            isNowVisible = false;
        }

        view.setNowButtonVisibility(isNowVisible);
    }

    private void updateDateLine(long timestamp) {
        int pos = 0;
        while (pos < dates.size() - 1 && dates.get(pos + 1) <= timestamp) {
            pos++;
        }
        if (pos != view.getSelectedDatePosition()) {
            view.setSelectedDate(dates.get(pos));
        }
    }

    private class ScheduleObserver extends BaseObserver<ChannelSchedulesResponse, EpgScreen> {

        ScheduleObserver(EpgScreen view) {
            super(view);
        }

        @Override
        public void onNext(ChannelSchedulesResponse response) {
            startDate = response.getStartDate();
            endDate = response.getEndDate();

            view.setDates(dates = getDates(startDate, endDate));
            view.setEpg(response.getChannels());
            view.hideProgress();

            updateNowButtonParameters(startDate);
        }
    }

    private static List<Long> getDates(long start, long end) {
        List<Long> dates = DateUtils.split(DateUtils.resetDay(start), end, DateUtils.DAY);
        if (!DateUtils.isSameDay(end, dates.get(dates.size() - 1))) {
            dates.add(end);
        }
        return dates;
    }
}
