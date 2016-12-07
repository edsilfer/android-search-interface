package br.com.edsilfer.android.sinterface.demo.util

import android.os.Environment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import br.com.edsilfer.android.sinterface.demo.UITestSearchSamples
import br.com.edsilfer.kotlin_support.extensions.sendEmail
import br.com.edsilfer.kotlin_support.model.Email
import br.com.edsilfer.kotlin_support.model.Sender
import org.apache.commons.io.FileUtils
import org.junit.ClassRule
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by User on 06/12/2016.
 */

object TestCommunicationManager {

    private val SCREEN_SHOTS_DIRECTORY = "${Environment.getExternalStorageDirectory()}/search-interface"
    private val SENDER_EMAIL = "cap.robot.jenkins@gmail.com"
    private val SENDER_PASSWORD = "sender123"
    private val EMAIL_SUBJECT = "Build Results: android-search-interface on ${SimpleDateFormat("yyyy-MM-dd_hh:mm:ss").format(Date())}"
    private val EMAIL_BODY = "UI Tests ran on ${SimpleDateFormat("yyyy-MM-dd_hh:mm:ss").format(Date())} resulting on attached screen shots."
    private val EMAIL_RECIPIENTS = mutableListOf("fernandes.s.edgar@gmail.com")

    fun sendNotificationEmail(activitySearch: AppCompatActivity) {
        try {
            val sender = Sender(SENDER_EMAIL, SENDER_PASSWORD)
            val email = Email(
                    EMAIL_SUBJECT,
                    EMAIL_BODY,
                    EMAIL_RECIPIENTS,
                    File(SCREEN_SHOTS_DIRECTORY).listFiles()
            )
            activitySearch.sendEmail(sender, email)
            FileUtils.cleanDirectory(File(SCREEN_SHOTS_DIRECTORY))
        } catch (e: Exception) {
            Log.e("E-MAIL MANAGER", e.message, e)
        }
    }
}

