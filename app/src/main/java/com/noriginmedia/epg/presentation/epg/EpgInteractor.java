package com.noriginmedia.epg.presentation.epg;

import com.noriginmedia.epg.common.DateUtils;
import com.noriginmedia.epg.data.network.NetworkDataSource;
import com.noriginmedia.epg.data.network.models.Channel;
import com.noriginmedia.epg.data.network.models.Schedule;
import com.noriginmedia.epg.data.network.models.response.ChannelSchedulesResponse;
import com.noriginmedia.epg.presentation.BaseInteractor;
import com.noriginmedia.epg.presentation.BaseObserver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.subjects.Subject;


public class EpgInteractor extends BaseInteractor {

    @Inject
    public EpgInteractor(NetworkDataSource networkDataSource, Subject<Boolean> networkStatePublisher) {
        super(networkDataSource, networkStatePublisher);
    }

    void getChannelSchedules(BaseObserver<ChannelSchedulesResponse, ?> observer) {
        startLoading(networkDataSource.getChannelSchedule(), observer)
                .map(this::initStartEndDates)
                .map(this::addPausesToChannel)
                .compose(retryNoNetwork(observer))
                .compose(manageSubscription())
                .subscribe(observer);
    }

    private ChannelSchedulesResponse initStartEndDates(ChannelSchedulesResponse response) {
        long startDate = Long.MAX_VALUE;
        long endDate = Long.MIN_VALUE;
        for (Channel channel : response.getChannels()) {
            for (Schedule schedule : channel.getSchedules()) {
                if (startDate > schedule.getStart()) {
                    startDate = schedule.getStart();
                }
                if (endDate < schedule.getEnd()) {
                    endDate = schedule.getEnd();
                }
            }
        }
        response.setStartDate(DateUtils.resetHour(startDate));
        response.setEndDate(DateUtils.resetHour(endDate) + DateUtils.HOUR);
        return response;
    }

    private ChannelSchedulesResponse addPausesToChannel(ChannelSchedulesResponse response) {
        long startDate = response.getStartDate();
        long endDate = response.getEndDate();
        for (Channel channel : response.getChannels()) {
            if (channel.getSchedules().isEmpty()) {
                channel.setSchedules(Collections.singletonList(new Pause(startDate, endDate)));
                continue;
            }

            List<Schedule> withoutPauses = channel.getSchedules();
            List<Schedule> schedules = new ArrayList<>();

            Schedule firstSchedule = withoutPauses.get(0);
            if (firstSchedule.getStart() > startDate) {
                schedules.add(new Pause(startDate, firstSchedule.getStart()));
            }

            for (int i = 0; i < withoutPauses.size() - 1; i++) {
                schedules.add(withoutPauses.get(i));
                long currentEnd = withoutPauses.get(i).getEnd();
                long nextStart = withoutPauses.get(i + 1).getStart();
                if (currentEnd != nextStart) {
                    schedules.add(new Pause(currentEnd, nextStart));
                }
            }

            Schedule lastSchedule = withoutPauses.get(withoutPauses.size() - 1);
            schedules.add(lastSchedule);
            if (lastSchedule.getEnd() < endDate) {
                schedules.add(new Pause(lastSchedule.getEnd(), endDate));
            }
            channel.setSchedules(schedules);
        }
        return response;
    }

    private static class Pause extends Schedule {

        Pause(long start, long end) {
            super(null, "", start, end);
        }
    }
}
