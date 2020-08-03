package com.byoutline.secretsauce.listeners;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

/**
 * Pauses picasso operations marked with given tag, when view
 * is scrolled quickly.
 *
 * @author Sebastian Kacprzak <sebastian.kacprzak at byoutline.com>
 */
public class PicassoScrollListener extends RecyclerView.OnScrollListener {
    private static final int SPEED_THRESHOLD = 24;
    private final Object tag;
    /**
     * @param tag must by equal to tag that was set in picasso
     */
    public PicassoScrollListener(Object tag) {
        this.tag = tag;
    }

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        int currentSpeed = Math.abs(dx) + Math.abs(dy);
        final Picasso picasso = Picasso.get();
        if (currentSpeed > SPEED_THRESHOLD) {
            picasso.pauseTag(tag);
        } else {
            picasso.resumeTag(tag);
        }
    }

    @Override
    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
        final Picasso picasso = Picasso.get();
        if (newState == RecyclerView.SCROLL_STATE_IDLE || newState == RecyclerView.SCROLL_STATE_DRAGGING) {
            picasso.resumeTag(tag);
        } else {
            picasso.pauseTag(tag);
        }
    }
}

