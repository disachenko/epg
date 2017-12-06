package com.noriginmedia.epg.common.view.recyclerview;


import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseViewHolder<E> extends RecyclerView.ViewHolder {

    public BaseViewHolder(ViewGroup parent, @LayoutRes int layoutRes) {
        this(LayoutInflater.from(parent.getContext()).inflate(layoutRes, parent, false));
    }

    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void bind(E item);
}
