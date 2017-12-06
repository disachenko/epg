package com.noriginmedia.epg.presentation.epg;

import android.view.ViewGroup;
import android.widget.TextView;

import com.noriginmedia.epg.R;
import com.noriginmedia.epg.common.DateUtils;
import com.noriginmedia.epg.common.view.recyclerview.BaseViewHolder;
import com.noriginmedia.epg.common.view.recyclerview.ClickableRecyclerAdapter;

public class DayLineAdapter extends ClickableRecyclerAdapter<Long, DayLineAdapter.DateViewHolder> {

    @Override
    public DateViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DateViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(DateViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    class DateViewHolder extends BaseViewHolder<Long> {

        private TextView dateTextView;

        DateViewHolder(ViewGroup parent) {
            super(parent, R.layout.list_item_day);
            dateTextView = (TextView) itemView;
        }

        @Override
        public void bind(Long timestamp) {
            dateTextView.setSelected(isItemSelected(timestamp));
            dateTextView.setText(String.format("%1$s\n%2$s",
                    DateUtils.getWeekDayName(itemView.getContext(), timestamp),
                    DateUtils.getLocalDayMonthString(timestamp)));
        }
    }

}
