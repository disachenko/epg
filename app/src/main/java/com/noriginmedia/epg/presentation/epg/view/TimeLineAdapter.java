package com.noriginmedia.epg.presentation.epg.view;


import android.view.ViewGroup;
import android.widget.TextView;

import com.noriginmedia.epg.R;
import com.noriginmedia.epg.common.DateUtils;
import com.noriginmedia.epg.common.view.recyclerview.BaseViewHolder;
import com.noriginmedia.epg.common.view.recyclerview.ClickableRecyclerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TimeLineAdapter extends ClickableRecyclerAdapter<Long, TimeLineAdapter.ViewHolder> {

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    class ViewHolder extends BaseViewHolder<Long> {

        @BindView(R.id.time)
        TextView timeTextView;

        ViewHolder(ViewGroup parent) {
            super(parent, R.layout.list_item_hour);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void bind(Long timestamp) {
            timeTextView.setText(DateUtils.getLocalTimeString(timestamp));
        }
    }
}
