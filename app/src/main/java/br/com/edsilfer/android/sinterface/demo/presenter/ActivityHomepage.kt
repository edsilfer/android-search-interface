package br.com.edsilfer.android.sinterface.demo.presenter

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import br.com.edsilfer.android.search_interface.presenter.activity.ActivitySearch
import br.com.edsilfer.android.sinterface.demo.R
import br.com.edsilfer.android.sinterface.demo.services.SearchManager
import br.com.edsilfer.kotlin_support.extensions.requestPermission
import br.com.edsilfer.kotlin_support.extensions.sendEmail
import br.com.edsilfer.kotlin_support.extensions.takeScreenShot
import br.com.edsilfer.kotlin_support.model.DirectoryPath
import br.com.edsilfer.kotlin_support.model.Email
import br.com.edsilfer.kotlin_support.model.Sender
import org.jetbrains.anko.doAsync
import java.io.File

/**
 * Created by User on 05/12/2016.
 */

class ActivityHomepage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_homepage)
        SearchManager()

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            takeScreenShot(location = DirectoryPath.EXTERNAL, path = "search-interface", openScreenShot = false)
        }
    }

    fun onSample01Clicked(view: View) {
        val intent = Intent(this, ActivitySearch::class.java)
        intent.putExtra(ActivitySearch.ARG_SEARCH_TEMPLATE, "search_template_01")
        startActivity(intent)
    }

    fun onSample02Clicked(view: View) {
        val intent = Intent(this, ActivitySearch::class.java)
        intent.putExtra(ActivitySearch.ARG_SEARCH_TEMPLATE, "search_template_02")
        startActivity(intent)
    }

    fun onSample03Clicked(view: View) {
        val intent = Intent(this, ActivitySearch::class.java)
        intent.putExtra(ActivitySearch.ARG_SEARCH_TEMPLATE, "search_template_03")
        startActivity(intent)
    }

    fun onTakeScreenShotClicked(view: View) {
        if (requestPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE))
            takeScreenShot(location = DirectoryPath.EXTERNAL, path = "search-interface", openScreenShot = false)
    }

    fun sendEmail() {
        doAsync {
            try {
                val sender = Sender("cap.robot.jenkins@gmail.com", "sender123")
                val email = Email(
                        "Hello world: SMTP Server from Android with Attachments",
                        "This is a sample e-mail sent via SMTP server from Android without the need of user interaction.",
                        mutableListOf("fernandes.s.edgar@gmail.com"),
                        File("${Environment.getExternalStorageDirectory().toString()}/search-interface").listFiles()
                )
                sendEmail(sender, email)
            } catch (e: Exception) {
                Log.e("SENDER E-MAIL SLAVE", e.message, e)
            }
        }
    }
}

