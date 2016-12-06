package br.com.edsilfer.android.sinterface.demo.presenter

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import br.com.edsilfer.android.search_interface.model.SearchPresets
import br.com.edsilfer.android.search_interface.presenter.activity.ActivitySearch
import br.com.edsilfer.android.sinterface.demo.R
import br.com.edsilfer.android.sinterface.demo.services.Samples
import br.com.edsilfer.android.sinterface.demo.services.SearchManager
import br.com.edsilfer.kotlin_support.extensions.requestPermission
import br.com.edsilfer.kotlin_support.extensions.takeScreenShot
import br.com.edsilfer.kotlin_support.model.DirectoryPath

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
        intent.putExtra(ActivitySearch.ARG_SEARCH_PRESET, SearchPresets.template01WithCircleResultRow())
        startActivity(intent)
    }

    fun onSample02Clicked(view: View) {
        val intent = Intent(this, ActivitySearch::class.java)
        intent.putExtra(ActivitySearch.ARG_SEARCH_PRESET, SearchPresets.template03WithSquareResultRow())
        startActivity(intent)
    }

    fun onSample03Clicked(view: View) {
        val intent = Intent(this, ActivitySearch::class.java)
        intent.putExtra(ActivitySearch.ARG_SEARCH_PRESET, Samples().sample01())
        startActivity(intent)
    }

    fun onSample04Clicked(view: View) {
        val intent = Intent(this, ActivitySearch::class.java)
        intent.putExtra(ActivitySearch.ARG_SEARCH_PRESET, Samples().sample01())
        startActivity(intent)
    }

    fun onTakeScreenShotClicked(view: View) {
        if (requestPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE))
            takeScreenShot(location = DirectoryPath.EXTERNAL, path = "search-interface", openScreenShot = false)
    }
}

