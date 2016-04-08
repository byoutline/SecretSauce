package com.byoutline.sampleapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.byoutline.sampleapplication.DialogSamples.DialogActivityExample;
import com.byoutline.sampleapplication.DrawableExample.DrawableActivityExample;
import com.byoutline.sampleapplication.baseCompatAppActivityExamples.BaseAppCompatExampleActivity;
import com.byoutline.sampleapplication.customViewsExample.CustomViewsExamples;
import com.byoutline.sampleapplication.datePickerExample.DatePickerSampleActivity;
import com.byoutline.sampleapplication.networkChangeExample.NetworkChangeActivity;
import com.byoutline.secretsauce.activities.WebViewActivityV7;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.baseAppCompatActivityButton:
                intent = new Intent(this, BaseAppCompatExampleActivity.class);
                startActivity(intent);
                break;
            case R.id.webVievActivityExample:
                /**
                 * first: remember about internet permission
                 * second: add this activity to manifest file
                 */
                intent = new Intent(this, WebViewActivityV7.class);
                intent.putExtra(WebViewActivityV7.BUNDLE_URL, "https://github.com/byoutline/SecretSauce");
                startActivity(intent);
                break;
            case R.id.networkChangeReceiverExample:
                intent = new Intent(this, NetworkChangeActivity.class);
                startActivity(intent);
                break;
            case R.id.datePickerExample:
                intent = new Intent(this, DatePickerSampleActivity.class);
                startActivity(intent);
                break;
            case R.id.roundedExample:
                intent = new Intent(this, DrawableActivityExample.class);
                startActivity(intent);
                break;
            case R.id.customViewsExample:
                intent = new Intent(this, CustomViewsExamples.class);
                startActivity(intent);
                break;
            case R.id.dialogExample:
                intent = new Intent(this, DialogActivityExample.class);
                startActivity(intent);
                break;
            default:

        }

    }
}
