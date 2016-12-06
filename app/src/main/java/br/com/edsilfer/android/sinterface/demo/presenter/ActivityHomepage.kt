package br.com.edsilfer.android.sinterface.demo.presenter

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import br.com.edsilfer.android.search_interface.model.SearchPresets
import br.com.edsilfer.android.search_interface.presenter.activity.ActivitySearch
import br.com.edsilfer.android.sinterface.demo.R
import br.com.edsilfer.android.sinterface.demo.services.Samples
import br.com.edsilfer.android.sinterface.demo.services.SearchManager

/**
 * Created by User on 05/12/2016.
 */

class ActivityHomepage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_homepage)
        SearchManager()
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
}

