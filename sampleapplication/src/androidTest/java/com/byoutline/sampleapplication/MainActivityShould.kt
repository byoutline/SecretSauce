package com.byoutline.sampleapplication

import android.support.v7.app.AppCompatActivity
import br.com.concretesolutions.kappuccino.custom.recyclerView.RecyclerViewInteractions.recyclerView
import com.byoutline.espressohelpers.activityTestRule
import com.schibsted.spain.barista.assertion.BaristaRecyclerViewAssertions.assertRecyclerViewItemCount
import org.junit.Rule
import org.junit.Test
import kotlin.reflect.KClass

class MainActivityShould {
    @get:Rule val rule = activityTestRule<MainActivity>(
        examples = listOf(
            i(AppCompatActivity::class, "Test example 0"),
            i(AppCompatActivity::class, "Test example 1"),
            i(AppCompatActivity::class, "Test example 2")
        )
    )

    @Test
    fun displayAllItemsFromRepository() {
        assertRecyclerViewItemCount(R.id.mainActivityRV, 3)
        recyclerView(R.id.mainActivityRV) {
            sizeIs(3)
            atPosition(0) {
                displayed { text("Test example 0 (AppCompatActivity)") }
            }
            atPosition(2) {
                displayed { text("Test example 2 (AppCompatActivity)") }
            }
        }
    }

    private fun i(clazz: KClass<out AppCompatActivity>, displayName: String) = MainActivityItem(clazz.java, displayName)
}

