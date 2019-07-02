package com.byoutline.sampleapplication

import androidx.appcompat.app.AppCompatActivity

abstract class ClassNameAsToolbarTitleActivity : AppCompatActivity() {
    override fun onResume() {
        super.onResume()
        title = javaClass.simpleName
    }
}