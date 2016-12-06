package br.com.edsilfer.android.sinterface.demo

import android.support.test.InstrumentationRegistry.getInstrumentation
import android.support.test.espresso.Espresso.closeSoftKeyboard
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.typeText
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.app.AppCompatActivity
import br.com.edsilfer.android.search_interface.presenter.activity.ActivitySearch
import br.com.edsilfer.android.sinterface.demo.presenter.ActivityHomepage
import br.com.edsilfer.kotlin_support.extensions.takeScreenShot
import br.com.edsilfer.kotlin_support.model.DirectoryPath
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by efernandes on 06/12/16.
 */

@RunWith(AndroidJUnit4::class)
@LargeTest
class TestSamples {

    companion object {
        private val SCREENSHOTS_DIRECTORY = "search-interface"
        private val TIME_OUT = 5000
        private val THUMBNAIL_BUFFER = 1500L
    }

    @get:Rule
    var mActivityRule = ActivityTestRule(ActivityHomepage::class.java)

    @Test
    fun launchSample01() {
        val monitor = getInstrumentation().addMonitor(ActivitySearch::class.java.name, null, false)
        onView(withId(R.id.btn_sample_01)).perform(click())
        onView(withId(R.id.input)).perform(typeText("Diana"))
        Thread.sleep(THUMBNAIL_BUFFER)
        closeSoftKeyboard()
        val activitySample01 = getInstrumentation().waitForMonitorWithTimeout(monitor, TIME_OUT.toLong()) as AppCompatActivity
        activitySample01.takeScreenShot(location = DirectoryPath.EXTERNAL, path = SCREENSHOTS_DIRECTORY, openScreenShot = false, showToast = false)
    }

    @Test
    fun launchSample02() {
        val monitor = getInstrumentation().addMonitor(ActivitySearch::class.java.name, null, false)
        onView(withId(R.id.btn_sample_02)).perform(click())
        onView(withId(R.id.input)).perform(typeText("Clark"))
        Thread.sleep(THUMBNAIL_BUFFER)
        closeSoftKeyboard()
        val activitySample01 = getInstrumentation().waitForMonitorWithTimeout(monitor, TIME_OUT.toLong()) as AppCompatActivity
        activitySample01.takeScreenShot(location = DirectoryPath.EXTERNAL, path = SCREENSHOTS_DIRECTORY, openScreenShot = false, showToast = false)
    }

    @Test
    fun launchSample03() {
        val monitor = getInstrumentation().addMonitor(ActivitySearch::class.java.name, null, false)
        onView(withId(R.id.btn_sample_03)).perform(click())
        onView(withId(R.id.input)).perform(typeText("Diana"))
        onView(withId(R.id.wrapper)).perform(click())
        onView(withId(R.id.input)).perform(typeText("Bruce"))
        onView(withId(R.id.wrapper)).perform(click())
        Thread.sleep(THUMBNAIL_BUFFER)
        closeSoftKeyboard()
        val activitySample01 = getInstrumentation().waitForMonitorWithTimeout(monitor, TIME_OUT.toLong()) as AppCompatActivity
        activitySample01.takeScreenShot(location = DirectoryPath.EXTERNAL, path = SCREENSHOTS_DIRECTORY, openScreenShot = false, showToast = false)
    }

}
