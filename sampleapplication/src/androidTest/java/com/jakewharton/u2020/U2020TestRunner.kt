package com.jakewharton.u2020

import android.annotation.SuppressLint
import android.app.KeyguardManager
import android.os.Bundle
import android.os.PowerManager
import android.os.PowerManager.ACQUIRE_CAUSES_WAKEUP
import android.os.PowerManager.FULL_WAKE_LOCK
import android.os.PowerManager.ON_AFTER_RELEASE
import androidx.test.runner.AndroidJUnitRunner
import androidx.core.content.systemService
import androidx.test.platform.app.InstrumentationRegistry
import com.byoutline.espressohelpers.ActivityRobot
import com.metova.cappuccino.animations.SystemAnimations

class U2020TestRunner : AndroidJUnitRunner() {
    private lateinit var wakeLock: PowerManager.WakeLock

    override fun onCreate(arguments: Bundle) {
        super.onCreate(arguments)
        ActivityRobot.takeScreenshots = arguments.getString("takeScreenshots", "false") == "true"
    }

    @Suppress("DEPRECATION")
    override fun onStart() {
        val app = targetContext.applicationContext
        val name = U2020TestRunner::class.java.simpleName

        // Unlock the device so that the tests can input keystrokes.
        app.systemService<KeyguardManager>().newKeyguardLock(name).disableKeyguard()

        // Wake up the screen.
        wakeLock = app.systemService<PowerManager>()
            .newWakeLock(FULL_WAKE_LOCK or ACQUIRE_CAUSES_WAKEUP or ON_AFTER_RELEASE, name)
        wakeLock.acquire()

        SystemAnimations.disableAll(InstrumentationRegistry.getTargetContext())

        super.onStart()
    }

    override fun finish(resultCode: Int, results: Bundle) {
        SystemAnimations.restoreAll(InstrumentationRegistry.getTargetContext())
        super.finish(resultCode, results)
    }

    @SuppressLint("MissingPermission")
    override fun onDestroy() {
        super.onDestroy()
        wakeLock.release()
    }
}