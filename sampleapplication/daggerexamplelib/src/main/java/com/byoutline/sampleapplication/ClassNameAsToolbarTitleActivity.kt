package com.byoutline.sampleapplication

import android.support.v7.app.AppCompatActivity


abstract class ClassNameAsToolbarTitleActivity: AppCompatActivity() {
    override fun onResume() {
        super.onResume()
        title = javaClass.simpleName
    }
}