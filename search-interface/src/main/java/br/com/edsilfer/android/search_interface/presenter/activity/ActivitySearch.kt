package br.com.edsilfer.android.search_interface.presenter.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import br.com.edsilfer.android.search_interface.R
import br.com.edsilfer.android.search_interface.model.ISearchInterface
import br.com.edsilfer.android.search_interface.model.ISearchListener
import br.com.edsilfer.android.search_interface.service.SearchBarManager

/**
 * Created by efernandes on 09/11/16.
 */

class ActivitySearch<T> : AppCompatActivity(), ISearchInterface<T> {

    companion object {
        val ACT_ARG_SEARCH_LISTENER = "searchlistener"
    }

    private var mSearchBar: SearchBarManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        mSearchBar = SearchBarManager.getInstance(this, intent.extras.getSerializable(ACT_ARG_SEARCH_LISTENER) as ISearchListener)
    }

    override fun updateResults(results: MutableList<T>) {

    }

}
