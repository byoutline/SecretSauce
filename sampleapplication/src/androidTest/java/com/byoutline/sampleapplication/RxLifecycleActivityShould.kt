package com.byoutline.sampleapplication

import br.com.concretesolutions.kappuccino.assertions.VisibilityAssertions.displayed
import com.byoutline.espressohelpers.activityTestRule
import com.byoutline.sampleapplication.rx.RxLifecycleActivity
import com.byoutline.sampleapplication.rx.RxLifecycleVM
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

class RxLifecycleActivityShould {
    @get:Rule val rule = activityTestRule<RxLifecycleActivity>()

    @Test
    fun displayProvidedValue() {
        displayed { text("Current Rx provided value: 42") }
    }
}

class RxLifecycleVMTest @Inject constructor() : RxLifecycleVM() {
    override fun onAttach(view: Any) {
        value.set(42)
    }
}

