package com.noriginmedia.epg.presentation.epg.view;


import android.support.design.widget.Snackbar;
import android.view.ViewGroup;
import android.widget.TextView;

import com.noriginmedia.epg.R;
import com.noriginmedia.epg.data.network.models.Schedule;
import com.noriginmedia.epg.common.DateUtils;
import com.noriginmedia.epg.common.view.recyclerview.BaseRecyclerAdapter;
import com.noriginmedia.epg.common.view.recyclerview.BaseViewHolder;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ScheduleAdapter extends BaseRecyclerAdapter<Schedule, ScheduleAdapter.ViewHolder> {


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    class ViewHolder extends BaseViewHolder<Schedule> {

        @BindView(R.id.title)
        TextView title;

        @BindView(R.id.time)
        TextView time;

        private Schedule schedule;

        ViewHolder(ViewGroup parent) {
            super(parent, R.layout.list_item_schedule);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(v -> Snackbar.make(itemView, schedule.getTitle(), Snackbar.LENGTH_SHORT).show());
        }

        @Override
        public void bind(Schedule schedule) {
            this.schedule = schedule;
            long startTime = schedule.getStart();
            long endTime = schedule.getEnd();
            updateViewWidth(startTime, endTime);
            updateBackground(startTime, endTime);

            boolean isEmpty = schedule.getId() == null;
            title.setText(schedule.getTitle());
            time.setText(isEmpty ? "" : DateUtils.getTimeRange(schedule.getStart(), schedule.getEnd()));
        }

        private void updateBackground(long startTime, long endTime) {
            long currentTime = new Date().getTime();
            boolean isActive = startTime < currentTime && currentTime < endTime;
            itemView.setSelected(isActive);
        }

        private void updateViewWidth(long startTime, long endTime) {
            int hourWidth = itemView.getResources().getDimensionPixelSize(R.dimen.time_line_hour_width);
            double duration = endTime - startTime;

            ViewGroup.LayoutParams params = itemView.getLayoutParams();
            params.width = (int) (duration / DateUtils.HOUR * hourWidth);
            itemView.requestLayout();
        }
    }
}
