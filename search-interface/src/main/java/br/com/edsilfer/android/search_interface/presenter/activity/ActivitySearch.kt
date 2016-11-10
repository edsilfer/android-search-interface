package br.com.edsilfer.android.search_interface.presenter.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.CardView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import br.com.edsilfer.android.lmanager.model.GenericHolderFactory
import br.com.edsilfer.android.lmanager.model.GenericViewHolder
import br.com.edsilfer.android.lmanager.model.IListControl
import br.com.edsilfer.android.lmanager.presenter.fragment.GenericListFragment
import br.com.edsilfer.android.search_interface.R
import br.com.edsilfer.android.search_interface.model.IResultRow
import br.com.edsilfer.android.search_interface.model.ISearchInterface
import br.com.edsilfer.android.search_interface.model.ISubscriber
import br.com.edsilfer.android.search_interface.model.enum.Events
import br.com.edsilfer.android.search_interface.model.viewholder.ResultViewHolder
import br.com.edsilfer.android.search_interface.service.SearchBarManager
import br.com.edsilfer.android.search_interface.service.SearchNotificationCenter
import kotlinx.android.synthetic.main.activity_search.*
import java.util.*

/**
 * Created by efernandes on 09/11/16.
 */

class ActivitySearch<T : IResultRow> : AppCompatActivity(), ISearchInterface<T>, ISubscriber {

    private var mListFragment: IListControl<T>? = null
    private var mSearchBar: SearchBarManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        mSearchBar = SearchBarManager.getInstance(this)
        SearchNotificationCenter.subscribe(Events.UPDATE_RESULTS, this)
    }

    private fun showResults(dataSet: ArrayList<T>) {
        mListFragment = GenericListFragment<T>().getInstance(
                dataSet,
                ResultItemFactory()
        )

        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.replaceable, mListFragment as Fragment)
        transaction.commit()
    }

    override fun execute(event: Events, payload: Any) {
        when (event) {
            Events.UPDATE_RESULTS -> updateResults(payload as MutableList<T>)
        }
    }

    override fun updateResults(results: MutableList<T>?) {
        if (null == results || results.size == 0) {
            replaceable.visibility = LinearLayout.GONE
            disclaimer.visibility = CardView.VISIBLE
            hideFragment()
        } else {
            replaceable.visibility = LinearLayout.VISIBLE
            disclaimer.visibility = CardView.GONE
            if (null == mListFragment) showResults(results as ArrayList<T>)
            else mListFragment!!.updateDataSet(results as ArrayList<T>)
            showFragment()
        }
    }

    private fun hideFragment() {
        if (null != mListFragment) {
            supportFragmentManager.beginTransaction()
                    .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                    .hide(mListFragment as Fragment)
                    .commit()
        }
    }

    private fun showFragment() {
        if (null != mListFragment) {
            supportFragmentManager.beginTransaction()
                    .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                    .show(mListFragment as Fragment)
                    .commit()
        }
    }

    class ResultItemFactory<in T : IResultRow> : GenericHolderFactory<T>() {
        override fun getViewHolder(view: ViewGroup): GenericViewHolder<T> {
            return ResultViewHolder(LayoutInflater.from(view.context).inflate(R.layout.rsc_result_row, view, false))
        }
    }
}
