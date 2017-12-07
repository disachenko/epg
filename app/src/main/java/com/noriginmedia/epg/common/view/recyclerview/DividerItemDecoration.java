package com.noriginmedia.epg.common.view.recyclerview;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Adds interior dividers to a RecyclerView with a LinearLayoutManager or its
 * subclass.<br/>
 *
 * @see <a href=
 * "https://github.com/bignerdranch/simple-item-decoration/blob/master/simpleitemdecoration/src/main/java/com/dgreenhalgh/android/simpleitemdecoration/linear/DividerItemDecoration.java">
 * source code</a>
 */
public class DividerItemDecoration extends RecyclerView.ItemDecoration {

    private Drawable divider;
    private int orientation;

    /**
     * Sole constructor. Takes in a {@link Drawable} to be used as the interior
     * divider.
     *
     * @param divider A divider {@code Drawable} to be drawn on the RecyclerView
     */
    public DividerItemDecoration(Drawable divider) {
        this.divider = divider;
    }

    /**
     * Draws horizontal or vertical dividers onto the parent RecyclerView.
     *
     * @param canvas The {@link Canvas} onto which dividers will be drawn
     * @param parent The RecyclerView onto which dividers are being added
     * @param state  The current RecyclerView.State of the RecyclerView
     */
    @Override
    public void onDraw(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        if (orientation == LinearLayoutManager.HORIZONTAL) {
            drawHorizontalDividers(canvas, parent);
        } else if (orientation == LinearLayoutManager.VERTICAL) {
            drawVerticalDividers(canvas, parent);
        }
    }

    /**
     * Determines the size and location of offsets between items in the parent
     * RecyclerView.
     *
     * @param outRect The {@link Rect} of offsets to be added around the child
     *                view
     * @param view    The child view to be decorated with an offset
     * @param parent  The RecyclerView onto which dividers are being added
     * @param state   The current RecyclerView.State of the RecyclerView
     */
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        if (parent.getChildAdapterPosition(view) == 0) {
            return;
        }

        orientation = ((LinearLayoutManager) parent.getLayoutManager()).getOrientation();
        if (orientation == LinearLayoutManager.HORIZONTAL) {
            outRect.left = divider.getIntrinsicWidth();
        } else if (orientation == LinearLayoutManager.VERTICAL) {
            outRect.top = divider.getIntrinsicHeight();
        }
    }

    /**
     * Adds dividers to a RecyclerView with a LinearLayoutManager or its
     * subclass oriented horizontally.
     *
     * @param canvas The {@link Canvas} onto which horizontal dividers will be
     *               drawn
     * @param parent The RecyclerView onto which horizontal dividers are being
     *               added
     */
    private void drawHorizontalDividers(Canvas canvas, RecyclerView parent) {
        int parentTop = parent.getPaddingTop();
        int parentBottom = parent.getHeight() - parent.getPaddingBottom();

        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount - 1; i++) {
            View child = parent.getChildAt(i);

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            int parentLeft = child.getRight() + params.rightMargin;
            int parentRight = parentLeft + divider.getIntrinsicWidth();

            divider.setBounds(parentLeft, parentTop, parentRight, parentBottom);
            divider.draw(canvas);
        }
    }

    /**
     * Adds dividers to a RecyclerView with a LinearLayoutManager or its
     * subclass oriented vertically.
     *
     * @param canvas The {@link Canvas} onto which vertical dividers will be
     *               drawn
     * @param parent The RecyclerView onto which vertical dividers are being
     *               added
     */
    private void drawVerticalDividers(Canvas canvas, RecyclerView parent) {
        int parentLeft = parent.getPaddingLeft();
        int parentRight = parent.getWidth() - parent.getPaddingRight();

        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount - 1; i++) {
            View child = parent.getChildAt(i);

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            int parentTop = child.getBottom() + params.bottomMargin;
            int parentBottom = parentTop + divider.getIntrinsicHeight();

            divider.setBounds(parentLeft, parentTop, parentRight, parentBottom);
            divider.draw(canvas);
        }
    }
}