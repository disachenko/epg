package com.noriginmedia.epg.presentation.epg;


import com.noriginmedia.epg.common.DateUtils;
import com.noriginmedia.epg.data.network.models.response.ChannelSchedulesResponse;
import com.noriginmedia.epg.presentation.BaseObserver;

import java.util.List;

import javax.inject.Inject;

public class EpgPresenter {

    private final EpgInteractor interactor;
    private EpgScreen view;

    private List<Long> dates;

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
            view.setDates(dates = getDates(response.getStartDate(), response.getEndDate()));
            view.setEpg(response.getChannels());
            view.hideProgress();
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
