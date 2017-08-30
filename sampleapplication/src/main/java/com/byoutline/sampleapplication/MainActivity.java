package com.byoutline.sampleapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.byoutline.sampleapplication.customfontviews.CustomFontViewsActivity;
import com.byoutline.sampleapplication.datepicker.DatePickerSampleActivity;
import com.byoutline.sampleapplication.draweractivity.ExampleActivity;
import com.byoutline.sampleapplication.networkchangereceiver.NetworkChangeActivity;
import com.byoutline.sampleapplication.roundedimageview.DrawableActivityExample;
import com.byoutline.secretsauce.activities.WebViewActivityV7;

public class MainActivity extends AppCompatActivity {

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
        switch (viewId) {
            case R.id.baseAppCompatActivityButton:
                return new Intent(this, ExampleActivity.class);
            case R.id.webVievActivityExample:
                // Remember about adding internet permission to manifest
                // and adding WebViewActivityV7 to manifest file.
                Intent intent = new Intent(this, WebViewActivityV7.class);
                intent.putExtra(WebViewActivityV7.BUNDLE_URL, "https://github.com/byoutline/SecretSauce");
                return intent;
            case R.id.networkChangeReceiverExample:
                return new Intent(this, NetworkChangeActivity.class);
            case R.id.datePickerExample:
                return new Intent(this, DatePickerSampleActivity.class);
            case R.id.roundedExample:
                return new Intent(this, DrawableActivityExample.class);
            case R.id.customViewsExample:
                return new Intent(this, CustomFontViewsActivity.class);
        }
        return null;
    }
}
