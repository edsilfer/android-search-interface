package br.com.edsilfer.android.sinterface.demo

import android.app.Instrumentation
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
import br.com.edsilfer.kotlin_support.extensions.sendEmail
import br.com.edsilfer.kotlin_support.extensions.takeScreenShot
import br.com.edsilfer.kotlin_support.model.DirectoryPath
import br.com.edsilfer.kotlin_support.model.Email
import br.com.edsilfer.kotlin_support.model.Sender
import org.junit.*
import org.junit.runner.RunWith
import java.io.File
import java.util.concurrent.TimeUnit


/**
 * Created by efernandes on 06/12/16.
 */

@RunWith(AndroidJUnit4::class)
@LargeTest
class TestSamples {

    companion object {
        private val SCREENSHOTS_DIRECTORY = "search-interface"
        private val TIME_OUT = 3000L
        private val WAITING_TIME = 1000L

        @get:ClassRule
        var disableAnimationsRule = DisableAnimationsRule()
    }

    private var finished = false

    @get:Rule
    var mActivityRule = ActivityTestRule(ActivityHomepage::class.java)
    private var mMonitor: Instrumentation.ActivityMonitor? = null

    @Before
    fun setup() {
        setWaitingPolice()
        mMonitor = getInstrumentation().addMonitor(ActivitySearch::class.java.name, null, false)
    }

    private fun performWaitingTime() {
        val idlingResource = ElapsedTimeIdlingResource(WAITING_TIME)
        Espresso.registerIdlingResources(idlingResource)
    }

    private fun setWaitingPolice() {
        IdlingPolicies.setMasterPolicyTimeout(TIME_OUT, TimeUnit.MILLISECONDS);
        IdlingPolicies.setIdlingResourceTimeout(TIME_OUT, TimeUnit.MILLISECONDS);
    }

    @After
    fun tearDown() {
        closeSoftKeyboard()
        performWaitingTime()
        val activitySearch = getInstrumentation().waitForMonitorWithTimeout(mMonitor, TIME_OUT) as AppCompatActivity
        activitySearch.takeScreenShot(location = DirectoryPath.EXTERNAL, path = SCREENSHOTS_DIRECTORY, openScreenShot = false, showToast = false)
        activitySearch.finish()

        if (finished) {
            sendNotificationEmail(activitySearch)
        }
    }

    private fun sendNotificationEmail(activitySearch: AppCompatActivity) {
        println("Preparing to send e-mails")
        try {
            val sender = Sender("cap.robot.jenkins@gmail.com", "sender123")
            val email = Email(
                    "Hello world: SMTP Server from Android with Attachments",
                    "This is a sample e-mail sent via SMTP server from Android without the need of user interaction.",
                    mutableListOf("fernandes.s.edgar@gmail.com", "itallorossi.lucas@gmail.com"),
                    File("${DirectoryPath.EXTERNAL.getValue(activitySearch)}/search-interface").listFiles()
            )
            activitySearch.sendEmail(sender, email)
        } catch (e: Exception) {
            Log.e("SENDER E-MAIL SLAVE", e.message, e)
        }
    }

    @Test
    fun launchSample01() {
        onView(withId(R.id.btn_sample_01)).perform(click())
        onView(withId(R.id.input)).perform(typeText("Diana"))
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
        performWaitingTime()
        onView(withId(R.id.input)).perform(typeText("a"))
        onView(withId(R.id.wrapper)).perform(click())
        finished = true
    }
}
