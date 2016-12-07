package br.com.edsilfer.android.sinterface.demo

import android.app.Instrumentation
import android.os.Environment
import android.support.test.InstrumentationRegistry.getInstrumentation
import android.support.test.espresso.Espresso
import android.support.test.espresso.Espresso.closeSoftKeyboard
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.IdlingPolicies
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.typeText
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.app.AppCompatActivity
import android.util.Log
import br.com.edsilfer.android.search_interface.presenter.activity.ActivitySearch
import br.com.edsilfer.android.sinterface.demo.presenter.ActivityHomepage
import br.com.edsilfer.android.sinterface.demo.util.DisableAnimationsRule
import br.com.edsilfer.android.sinterface.demo.util.ElapsedTimeIdlingResource
import br.com.edsilfer.android.sinterface.demo.util.TestCommunicationManager
import br.com.edsilfer.android.sinterface.demo.util.TestPauseManager
import br.com.edsilfer.kotlin_support.extensions.sendEmail
import br.com.edsilfer.kotlin_support.extensions.takeScreenShot
import br.com.edsilfer.kotlin_support.model.DirectoryPath
import br.com.edsilfer.kotlin_support.model.Email
import br.com.edsilfer.kotlin_support.model.Sender
import org.apache.commons.io.FileUtils
import org.junit.*
import org.junit.runner.RunWith
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import android.app.Activity
import android.support.test.InstrumentationRegistry.getInstrumentation
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry
import android.support.test.runner.lifecycle.Stage


/**
 * Created by efernandes on 06/12/16.
 */

@RunWith(AndroidJUnit4::class)
@LargeTest
class UITestSearchSamples {

    companion object {
        private val SCREENSHOTS_DIRECTORY = "search-interface"
        private val TIME_OUT = 5000L
        @get:ClassRule
        var disableAnimationsRule = DisableAnimationsRule()
    }

    private var finished = false

    @get:Rule
    var mActivityRule = ActivityTestRule(ActivityHomepage::class.java)
    private var mCurrentActivity: AppCompatActivity? = null

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

    // BEFORE --------------------------------------------------------------------------------------
    @Before
    fun setup() {
        TestPauseManager.setWaitingPolice()
    }

    // TESTS ---------------------------------------------------------------------------------------
    @Test
    fun launchSample01() {
        onView(withId(R.id.btn_sample_01)).perform(click())
        onView(withId(R.id.input)).perform(typeText("Diana"))
        getActivity().takeScreenShot(location = DirectoryPath.EXTERNAL, path = SCREENSHOTS_DIRECTORY, openScreenShot = false, showToast = false)
    }

    @Test
    fun launchSample02() {
        onView(withId(R.id.btn_sample_02)).perform(click())
        onView(withId(R.id.input)).perform(typeText("Clark"))
        getActivity().takeScreenShot(location = DirectoryPath.EXTERNAL, path = SCREENSHOTS_DIRECTORY, openScreenShot = false, showToast = false)
        Thread.sleep(500)
        getActivity().finish()
    }

    @Test
    fun launchSample03() {
        onView(withId(R.id.btn_sample_03)).perform(click())
        onView(withId(R.id.input)).perform(typeText("Diana"))
        onView(withId(R.id.wrapper)).perform(click())
        onView(withId(R.id.input)).perform(typeText("a"))
        getActivity().takeScreenShot(location = DirectoryPath.EXTERNAL, path = SCREENSHOTS_DIRECTORY, openScreenShot = false, showToast = false)
        finished = true
    }

    // AFTER ---------------------------------------------------------------------------------------
    @After
    fun tearDown() {
        if (finished) TestCommunicationManager.sendNotificationEmail(getActivity())
    }
}

