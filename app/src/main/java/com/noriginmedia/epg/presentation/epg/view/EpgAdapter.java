package com.noriginmedia.epg.presentation.epg.view;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.RequestManager;
import com.noriginmedia.epg.R;
import com.noriginmedia.epg.common.DateUtils;
import com.noriginmedia.epg.common.view.recyclerview.BaseRecyclerAdapter;
import com.noriginmedia.epg.common.view.recyclerview.BaseViewHolder;
import com.noriginmedia.epg.data.network.models.Channel;
import com.noriginmedia.epg.data.network.models.Schedule;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EpgAdapter extends BaseRecyclerAdapter<Channel, EpgAdapter.ChanelViewHolder> {

    private EpgScrollListener channelScrollListener;
    private RequestManager imageRequestManager;
    private int hourWidth;

    EpgAdapter(EpgScrollListener channelScrollListener, RequestManager imageRequestManager, int hourWidth) {
        this.channelScrollListener = channelScrollListener;
        this.imageRequestManager = imageRequestManager;
        this.hourWidth = hourWidth;
    }

    @Override
    public ChanelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ChanelViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(ChanelViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    @Override
    public void onViewAttachedToWindow(ChanelViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        holder.handleScroll(getItem(holder.getAdapterPosition()));
    }

    class ChanelViewHolder extends BaseViewHolder<Channel> {

        @BindView(R.id.icon)
        ImageView icon;

        @BindView(R.id.schedule)
        RecyclerView schedule;
        private ScheduleAdapter scheduleAdapter;
        private LinearLayoutManager layoutManager;

        ChanelViewHolder(ViewGroup parent) {
            super(parent, R.layout.list_item_channel);
            ButterKnife.bind(this, itemView);

            layoutManager = new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.HORIZONTAL, false);
            schedule.setLayoutManager(layoutManager);
            schedule.setAdapter(scheduleAdapter = new ScheduleAdapter(hourWidth));
        }

        @Override
        public void bind(Channel channel) {
            scheduleAdapter.setItems(channel.getSchedules());
            imageRequestManager.load(channel.getImages().getLogo()).into(icon);
            handleScroll(channel);
        }

        private void handleScroll(Channel channel) {
            schedule.removeOnScrollListener(channelScrollListener);

            double xScroll = channelScrollListener.getOverallXScroll();
            double timeShift = xScroll / hourWidth * DateUtils.HOUR;
            int pos = 0;
            long time = 0;
            //TODO think about using binary search instead of it
            for (Schedule schedule : channel.getSchedules()) {
                pos++;
                time += schedule.getEnd() - schedule.getStart();
                if (time >= timeShift) {
                    break;
                }
            }
            int offset = (int) (((double) time - timeShift) / DateUtils.HOUR * hourWidth);
            layoutManager.scrollToPositionWithOffset(pos, offset);

            schedule.addOnScrollListener(channelScrollListener);
        }
    }
}
