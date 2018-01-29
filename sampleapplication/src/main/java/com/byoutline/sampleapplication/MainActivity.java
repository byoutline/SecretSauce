package com.byoutline.sampleapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.byoutline.sampleapplication.customfontviews.CustomFontViewsActivity;
import com.byoutline.sampleapplication.databinding.DataBindingActivity;
import com.byoutline.sampleapplication.draweractivity.ExampleActivity;
import com.byoutline.sampleapplication.networkchangereceiver.NetworkChangeActivity;
import com.byoutline.sampleapplication.roundedimageview.DrawableActivityExample;
import com.byoutline.sampleapplication.rx.RxLifecycleActivity;
import com.byoutline.sampleapplication.waitlayout.WaitActivity;
import com.byoutline.secretsauce.activities.WebViewActivityV7;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private final Map<Integer, Class<? extends AppCompatActivity>> btnClassMap = new HashMap<Integer, Class<? extends AppCompatActivity>>() {{
        put(R.id.baseAppCompatActivityBtn , ExampleActivity.class);
        put(R.id.networkChangeReceiverBtn , NetworkChangeActivity.class);
        put(R.id.roundedBtn , DrawableActivityExample.class);
        put(R.id.customViewsBtn , CustomFontViewsActivity.class);
        put(R.id.waitLayoutBtn , WaitActivity.class);
        put(R.id.rxBtn , RxLifecycleActivity.class);
        put(R.id.databindingBtn , DataBindingActivity.class);
    }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view) {
        Intent intent = getActivityIntentForView(view.getId());
        if (intent != null) {
            startActivity(intent);
        }
    }

    @Nullable
    private Intent getActivityIntentForView(@IdRes int viewId) {
        if(viewId == R.id.webVievActivityBtn) {
            // Remember about adding internet permission to manifest
            // and adding WebViewActivityV7 to manifest file.
            Intent intent = new Intent(this, WebViewActivityV7.class);
            intent.putExtra(WebViewActivityV7.BUNDLE_URL, "https://github.com/byoutline/SecretSauce");
            return intent;
        }
        if(btnClassMap.containsKey(viewId)) {
            return new Intent(this, btnClassMap.get(viewId));
        }
        return null;
    }
}
