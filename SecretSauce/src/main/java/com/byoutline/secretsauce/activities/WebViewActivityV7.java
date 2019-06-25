package com.byoutline.secretsauce.activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.webkit.WebView;

import com.byoutline.secretsauce.R;
import com.byoutline.secretsauce.utils.LogUtils;
import com.byoutline.shadow.org.apache.http.util.EncodingUtils;

public class WebViewActivityV7 extends AppCompatActivity {

    public static final String BUNDLE_URL = "bundle_url";
    public static final String BUNDLE_POST_DATA = "bundle_post_data";
    private static final String TAG = LogUtils.makeLogTag(WebViewActivityV7.class);
    WebView webview;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        webview = findViewById(R.id.webview);
        configureWebView();
        if (webview.getUrl() == null) {
            setDataFromArgs();
        }
    }

    private void setDataFromArgs() {
        Intent intent = getIntent();
        if (intent == null) {
            LogUtils.LOGE(TAG, "Null intent"); // NOI18E
            return;
        }
        Bundle args = intent.getExtras();
        if (args == null) {
            LogUtils.LOGE(TAG, "Null args"); // NOI18E
            return;
        }
        String url = args.getString(BUNDLE_URL);
        String postData = args.getString(BUNDLE_POST_DATA);
        boolean argsInvalid = TextUtils.isEmpty(url);
        if (argsInvalid) {
            logCreatedWithInvalidArgs(args);
        }
        byte[] postBytes = postData == null ? new byte[0] : EncodingUtils.getBytes(postData, "UTF-8");
        // without this line, chromium webview (android 4.4+) does not display PayU page
        webview.getSettings().setUseWideViewPort(false);
        if (postBytes.length == 0) {
            webview.loadUrl(url);
        } else {
            webview.postUrl(url, postBytes);
        }
    }

    private void logCreatedWithInvalidArgs(Bundle args) {
        LogUtils.LOGE(TAG, "Created with invalid arguments: " + args); // NOI18E
    }


    private void configureWebView() {
        webview.getSettings().setJavaScriptEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            try {
                onBackPressed();
            } catch (IllegalStateException ex) {
                // WORKAROUND for onOptionsItemSelected getting called after onSavedInstanceState.
                return super.onOptionsItemSelected(item);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webview.canGoBack()) {
            webview.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        webview.saveState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        webview.restoreState(savedInstanceState);
    }
}
