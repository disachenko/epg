package com.noriginmedia.epg.common.view.recyclerview;


import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseRecyclerAdapter<E, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    private List<E> objects = new ArrayList<>();

    @Nullable
    private E selectedItem;

    public void setItems(List<E> entities) {
        objects = new ArrayList<>(entities);
        notifyDataSetChanged();
    }

    public List<E> getItems() {
        return objects;
    }

    public E getItem(int position) {
        return objects.get(position);
    }

    @Override
    public int getItemCount() {
        return getObjectsListSize();
    }

    public int getObjectsListSize() {
        return objects.size();
    }

    public void setSelectedItem(@Nullable E item) {
        if ((item == null) ? (selectedItem == null) : item.equals(selectedItem)) {
            return;
        }
        this.selectedItem = item;
        notifyDataSetChanged();
    }

    /**
     * @return -1, if no one item is selected
     */
    public int getSelectedItemPosition() {
        return selectedItem == null ? -1 : objects.indexOf(selectedItem);
    }

    public boolean isItemSelected(E item) {
        return selectedItem != null && selectedItem.equals(item);
    }

    @Nullable
    public E getSelectedItemData() {
        return selectedItem;
    }
}
