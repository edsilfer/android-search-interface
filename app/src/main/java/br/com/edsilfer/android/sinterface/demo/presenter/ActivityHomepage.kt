package br.com.edsilfer.android.sinterface.demo.presenter

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import br.com.edsilfer.android.search_interface.model.ISearchListener
import br.com.edsilfer.android.search_interface.presenter.activity.ActivitySearch
import br.com.edsilfer.kotlin_support.extensions.log

/**
 * Created by efernandes on 09/11/16.
 */

class ActivityHomepage : AppCompatActivity() {

    private var mSearchListener: SearchHandler = SearchHandler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startSearchActivity()
    }

    private fun startSearchActivity() {
        val intent = Intent(this, ActivitySearch::class.java)
        intent.putExtra(ActivitySearch.ACT_ARG_SEARCH_LISTENER, mSearchListener)
        startActivity(intent)
    }

    class SearchHandler : ISearchListener {
        override fun onSearchTyped(query: String) {
            log("user typed: $query")
        }
    }

}


