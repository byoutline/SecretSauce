package com.byoutline.secretsauce.databinding;


import androidx.databinding.BindingAdapter;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class BindingAdapters {
    @BindingAdapter("showView")
    public static void showView(View view, boolean show) {
        if (show) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
    }

    @BindingAdapter("active")
    public static void setActivated(View view, boolean activated) {
        view.setActivated(activated);
    }

    @BindingAdapter("onClick")
    public static void onClick(View view, final Runnable listener) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.run();
            }
        });
    }

    @BindingAdapter("error")
    public static void onEditTextError(EditText editText, String text) {
        editText.setError(text);
    }

    @BindingAdapter("srcId")
    public static void setImageViewResource(ImageView imageView, int resource) {
        imageView.setImageResource(resource);
    }
}
