package br.com.edsilfer.samples.searchinterface

import android.os.Environment
import android.support.test.InstrumentationRegistry.getInstrumentation
import android.support.test.espresso.Espresso
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.NoActivityResumedException
import android.support.test.espresso.UiController
import android.support.test.espresso.ViewAction
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.typeText
import android.support.test.espresso.matcher.ViewMatchers.isRoot
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry
import android.support.test.runner.lifecycle.Stage
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import br.com.edsilfer.samples.searchinterface.presenter.ActivityHomepage
import br.com.edsilfer.samples.searchinterface.util.TestCommunicationManager
import br.com.edsilfer.kotlin_support.extensions.takeScreenShot
import br.com.edsilfer.kotlin_support.model.DirectoryPath
import br.com.edsilfer.samples.search_interface.R
import org.apache.commons.io.FileUtils
import org.hamcrest.Matcher
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.File


/**
 * Created by efernandes on 06/12/16.
 */

@RunWith(AndroidJUnit4::class)
@LargeTest
class UITestSearchSamples {

    companion object {
        private val SCREENSHOTS_DIRECTORY = "search-interface"
        private val NUMBER_OF_BACKS = 3
        private val WAITING_TIME = 1500L
    }

    private var finished = false
    private var mCurrentActivity: AppCompatActivity? = null

    @get:Rule
    var mActivityRule = ActivityTestRule(ActivityHomepage::class.java, false, true)

    @Test
    fun launchSample01() {
        onView(withId(R.id.btn_sample_01)).perform(click())
        onView(withId(R.id.input)).perform(typeText("Diana"))
        waitFor(WAITING_TIME)
    }

    @Test
    fun launchSample02() {
        onView(withId(R.id.btn_sample_02)).perform(click())
        onView(withId(R.id.input)).perform(typeText("Clark"))
    }

    @Test
    fun launchSample03() {
        onView(withId(R.id.btn_sample_03)).perform(click())
        onView(withId(R.id.input)).perform(typeText("Diana"))
        onView(withId(R.id.wrapper)).perform(click())
        onView(withId(R.id.input)).perform(typeText("a"))
        waitFor(WAITING_TIME)
        finished = true
    }

    @After
    fun tearDown() {
        getActivity().takeScreenShot(location = DirectoryPath.EXTERNAL, path = SCREENSHOTS_DIRECTORY, openScreenShot = false, showToast = false)
        waitFor(WAITING_TIME)
        if (finished) {
            TestCommunicationManager.sendNotificationEmail(mActivityRule.activity)
            FileUtils.cleanDirectory(File("${Environment.getExternalStorageDirectory()}/$SCREENSHOTS_DIRECTORY"))
        }
        closeActivity()
    }

    // UTILITY =====================================================================================
    private fun getActivity(): AppCompatActivity {
        getInstrumentation().runOnMainSync {
            val resumedActivity = ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED)
            for (act in resumedActivity) {
                mCurrentActivity = act as AppCompatActivity
                break
            }
        }
        return mCurrentActivity!!
    }

    /**
     * Required to close the activity when test is finishing, allowing the execution of the repetition behavior
     */
    private fun closeActivity() {
        try {
            for (i in 0..NUMBER_OF_BACKS - 1) {
                Espresso.pressBack()
            }
        } catch (e: NoActivityResumedException) {
            Log.e(UITestSearchSamples::class.java.simpleName, "Unable to close activity", e)
        }

    }

    /**
     * Perform action of waiting for a specific time.
     */
    fun waitFor(millis: Long): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                return isRoot()
            }

            override fun getDescription(): String {
                return "Wait for $millis milliseconds."
            }

            override fun perform(uiController: UiController, view: View) {
                uiController.loopMainThreadForAtLeast(millis)
            }
        }
    }
}

