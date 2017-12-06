package com.noriginmedia.epg.common.view.recyclerview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

public abstract class ClickableRecyclerAdapter<E, VH extends RecyclerView.ViewHolder>
        extends BaseRecyclerAdapter<E, VH> {

    private OnItemClickListener<E> listener;

    @Override
    public void onBindViewHolder(VH holder, int position, List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);

        if (listener == null) {
            return;
        }

        boolean isClickable = isItemClickable(position);
        holder.itemView.setOnClickListener(isClickable ?
                new OnRecyclerViewItemClickListener<>(listener, getItem(position), position)
                : null);
    }

    public void setOnItemClickListener(OnItemClickListener<E> listener) {
        this.listener = listener;
    }

    public boolean isItemClickable(int position) {
        return true;
    }

    public interface OnItemClickListener<E> {

        /**
         * @param view     of item
         * @param item     from source
         * @param position of item in source base_list
         */
        void onItemClicked(@NonNull View view, E item, int position);
    }
}
