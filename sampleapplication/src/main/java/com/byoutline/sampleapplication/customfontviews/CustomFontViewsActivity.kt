package com.byoutline.sampleapplication.customfontviews

import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.widget.TextView
import com.byoutline.sampleapplication.ClassNameAsToolbarTitleActivity
import com.byoutline.sampleapplication.R
import com.byoutline.secretsauce.utils.showToast
import com.byoutline.secretsauce.views.LinkTextView

class CustomFontViewsActivity : ClassNameAsToolbarTitleActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_views_examples)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val linkListener = findViewById<LinkTextView>(R.id.link_listener)

        linkListener.setText(R.string.custom_font_views_html_string, TextView.BufferType.NORMAL)
        linkListener.setOnLinkClickListener { this@CustomFontViewsActivity.showToast(R.string.custom_font_views_toast) }
    }
}
