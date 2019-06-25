package com.byoutline.secretsauce.listeners;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

/**
 * Pauses picasso operations marked with given context tag, when view
 * is scrolled quickly.
 *
 * @author Sebastian Kacprzak <sebastian.kacprzak at byoutline.com>
 */
public class PicassoScrollListener extends RecyclerView.OnScrollListener {
    private static final int SPEED_THRESHOLD = 24;
    private final Context context;

    /**
     * @param context must by equal to tag that was set in picasso(usually activity context).
     */
    public PicassoScrollListener(Context context) {
        this.context = context;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        int currentSpeed = Math.abs(dx) + Math.abs(dy);
        final Picasso picasso = Picasso.get();
        if (currentSpeed > SPEED_THRESHOLD) {
            picasso.pauseTag(context);
        } else {
            picasso.resumeTag(context);
        }
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        final Picasso picasso = Picasso.get();
        if (newState == RecyclerView.SCROLL_STATE_IDLE || newState == RecyclerView.SCROLL_STATE_DRAGGING) {
            picasso.resumeTag(context);
        } else {
            picasso.pauseTag(context);
        }
    }
}

