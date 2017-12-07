package com.noriginmedia.epg.presentation.epg.view;


import android.view.ViewGroup;
import android.widget.TextView;

import com.noriginmedia.epg.R;
import com.noriginmedia.epg.common.DateUtils;
import com.noriginmedia.epg.common.view.recyclerview.BaseViewHolder;
import com.noriginmedia.epg.common.view.recyclerview.ClickableRecyclerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TimelineAdapter extends ClickableRecyclerAdapter<Long, TimelineAdapter.HourViewHolder> {

    private static final int FULL_HOUR_TYPE = 7441;
    private static final int PART_OF_HOUR_TYPE = 7442;

    private int hourWidth;
    private int leftTextPadding;

    TimelineAdapter(int hourWidth, int leftTextPadding) {
        this.hourWidth = hourWidth;
        this.leftTextPadding = leftTextPadding;
    }

    @Override
    public HourViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return viewType == FULL_HOUR_TYPE ? new FullHourViewHolder(parent) : new PartHourViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(HourViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    @Override
    public int getItemViewType(int position) {
        return position != (getItemCount() - 1) ? FULL_HOUR_TYPE : PART_OF_HOUR_TYPE;
    }

    static abstract class HourViewHolder extends BaseViewHolder<Long> {

        @BindView(R.id.time_container)
        ViewGroup timeContainer;

        @BindView(R.id.time)
        TextView timeTextView;

        HourViewHolder(ViewGroup parent) {
            super(parent, R.layout.list_item_hour);
            ButterKnife.bind(this, itemView);
            setupLayoutParams();
        }

        protected abstract void setupLayoutParams();

        @Override
        public void bind(Long timestamp) {
            timeTextView.setText(DateUtils.getLocalTimeString(timestamp));
        }
    }

    class FullHourViewHolder extends HourViewHolder {

        FullHourViewHolder(ViewGroup parent) {
            super(parent);
        }

        @Override
        protected void setupLayoutParams() {
            ViewGroup.LayoutParams params = itemView.getLayoutParams();
            params.width = hourWidth;
            params = timeContainer.getLayoutParams();
            params.width = leftTextPadding * 2;
            itemView.requestLayout();
        }
    }

    class PartHourViewHolder extends HourViewHolder {

        PartHourViewHolder(ViewGroup parent) {
            super(parent);
        }

        @Override
        protected void setupLayoutParams() {
            ViewGroup.LayoutParams params = itemView.getLayoutParams();
            params.width = leftTextPadding;
            params = timeContainer.getLayoutParams();
            params.width = 0;
            itemView.requestLayout();
        }
    }
}
