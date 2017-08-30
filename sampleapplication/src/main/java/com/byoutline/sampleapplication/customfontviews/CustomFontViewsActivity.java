package com.byoutline.sampleapplication.customfontviews;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import com.byoutline.sampleapplication.R;
import com.byoutline.secretsauce.utils.ViewUtils;
import com.byoutline.secretsauce.views.LinkClickListener;
import com.byoutline.secretsauce.views.LinkTextView;

public class CustomFontViewsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_views_examples);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        LinkTextView linkListener = findViewById(R.id.link_listener);

        linkListener.setText(R.string.custom_font_views_html_string, TextView.BufferType.NORMAL);
        linkListener.setOnLinkClickListener(new LinkClickListener() {
            @Override
            public void onClick(String url) {
                ViewUtils.showToast(R.string.custom_font_views_toast);
            }
        });
    }
}
