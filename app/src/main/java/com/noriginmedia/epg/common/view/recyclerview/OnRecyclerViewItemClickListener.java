package com.noriginmedia.epg.common.view.recyclerview;

import android.view.View;

public class OnRecyclerViewItemClickListener<E> implements View.OnClickListener {

    private ClickableRecyclerAdapter.OnItemClickListener<E> listener;
    private E entity;
    private int position;

    public OnRecyclerViewItemClickListener(ClickableRecyclerAdapter.OnItemClickListener<E> listener, E entity, int position) {
        this.listener = listener;
        this.entity = entity;
        this.position = position;
    }

    @Override
    public void onClick(View view) {
        listener.onItemClicked(view, entity, position);
    }
}