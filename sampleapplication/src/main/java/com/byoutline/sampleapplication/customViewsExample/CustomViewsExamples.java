package com.byoutline.sampleapplication.customViewsExample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import com.byoutline.sampleapplication.R;
import com.byoutline.secretsauce.views.LinkClickListener;
import com.byoutline.secretsauce.views.LinkTextView;

import butterknife.ButterKnife;

public class CustomViewsExamples extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_views_examples);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        LinkTextView linkListener =(LinkTextView)findViewById(R.id.link_listener);
        String htmlString = "Click on <a href=https://github.com/byoutline/SecretSauce>" +
               "https://github.com/byoutline/SecretSauce </a> or click <a href=here>here</a> to see toast";

        linkListener.setText(htmlString, TextView.BufferType.NORMAL);
        linkListener.setOnLinkClickListener(new LinkClickListener() {
            @Override
            public void onClick(String url) {
                Toast.makeText(getApplicationContext(),"Click on URL in textView",Toast.LENGTH_SHORT).show();
            }
        });

    }

}
