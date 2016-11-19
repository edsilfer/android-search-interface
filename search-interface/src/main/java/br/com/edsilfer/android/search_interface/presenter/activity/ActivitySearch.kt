package br.com.edsilfer.android.search_interface.presenter.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.view.ContextThemeWrapper
import android.support.v7.widget.CardView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import br.com.edsilfer.android.lmanager.model.GenericHolderFactory
import br.com.edsilfer.android.lmanager.model.GenericViewHolder
import br.com.edsilfer.android.lmanager.model.IListControl
import br.com.edsilfer.android.lmanager.presenter.fragment.GenericListFragment
import br.com.edsilfer.android.search_interface.R
import br.com.edsilfer.android.search_interface.model.IResultRow
import br.com.edsilfer.android.search_interface.model.ISearchInterface
import br.com.edsilfer.android.search_interface.model.ISubscriber
import br.com.edsilfer.android.search_interface.model.SearchPallet
import br.com.edsilfer.android.search_interface.model.enum.Events
import br.com.edsilfer.android.search_interface.model.viewholder.ResultViewHolder
import br.com.edsilfer.android.search_interface.service.NotificationCenter
import br.com.edsilfer.android.search_interface.service.SearchBar
import br.com.edsilfer.kotlin_support.extensions.hideIndeterminateProgressBar
import br.com.edsilfer.kotlin_support.extensions.paintStatusBar
import br.com.edsilfer.kotlin_support.extensions.showIndeterminateProgressBar
import kotlinx.android.synthetic.main.activity_search.*
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.image
import java.util.*


/**
 * Created by efernandes on 09/11/16.
 */

class ActivitySearch<T : IResultRow> : AppCompatActivity(), ISearchInterface<T>, ISubscriber {

    companion object {
        val ARG_SEARCH_PRESET = "searchpreset"
    }

    private var mListFragment: IListControl<T>? = null
    private var mSearchBar: SearchBar? = null
    private var mPreset: SearchPallet? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        retrievePreset()
        mSearchBar = SearchBar(this, mPreset!!.searchBar)
        paintStatusBar(mPreset!!.searchBar.colorPrimaryDark)
        NotificationCenter.subscribe(Events.UPDATE_RESULTS, this)
        configureUserInterface()
        loadFragment(arrayListOf<T>())
    }

    private fun configureUserInterface() {
        setBackgroundPreset()
        setNoResultsDisclaimer()
    }

    private fun setNoResultsDisclaimer() {
        message_wrapper.setCardBackgroundColor(resources.getColor(mPreset!!.resultDisclaimer.backgroundColor))
        val disclaimer = TextView(ContextThemeWrapper(this, mPreset!!.resultDisclaimer.style), null, 0)
        disclaimer.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        disclaimer.textAlignment = TextView.TEXT_ALIGNMENT_CENTER
        disclaimer.text = getString(mPreset!!.resultDisclaimer.message)
        message_wrapper.addView(disclaimer)
    }

    private fun setBackgroundPreset() {
        if (mPreset!!.background.drawable != -1) {
            background.image = getDrawable(mPreset!!.background.drawable)
            background.alpha = mPreset!!.background.alpha
        } else {
            container.backgroundColor = mPreset!!.background.color
            container.alpha = mPreset!!.background.alpha
        }
    }

    private fun retrievePreset() {
        intent.extras.getSerializable(ARG_SEARCH_PRESET) ?: throw IllegalArgumentException(getString(R.string.str_exceptions_preset_not_found))
        mPreset = intent.extras.getSerializable(ARG_SEARCH_PRESET) as SearchPallet
    }

    private fun loadFragment(dataSet: ArrayList<T>) {
        if (!isFinishing) {
            mListFragment = GenericListFragment<T>().getInstance(
                    dataSet,
                    ResultItemFactory(mPreset!!.resultRow)
            )

            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.replaceable, mListFragment as Fragment)
                    .commit()
        }
    }

    /**
     * NOTIFICATION CENTER EVENT HANDLER
     */
    override fun execute(event: Events, payload: Any) {
        when (event) {
            Events.UPDATE_RESULTS -> updateResults(payload as MutableList<T>)
            else -> {
                // DO NOTHING
            }
        }
    }

    override fun updateResults(results: MutableList<T>?) {
        hideIndeterminateProgressBar()
        if (null == results || results.size == 0) {
            replaceable.visibility = LinearLayout.GONE
            message_wrapper.visibility = CardView.VISIBLE
            hideFragment()
        } else {
            replaceable.visibility = LinearLayout.VISIBLE
            message_wrapper.visibility = CardView.GONE
            mListFragment!!.updateDataSet(results as ArrayList<T>)
            showFragment()
        }
    }

    private fun hideFragment() {
        runOnUiThread {
            if (null != mListFragment) {
                supportFragmentManager.beginTransaction()
                        .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                        .hide(mListFragment as Fragment)
                        .commitAllowingStateLoss()
            }
        }
    }

    private fun showFragment() {
        runOnUiThread {
            if (null != mListFragment) {
                supportFragmentManager.beginTransaction()
                        .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                        .show(mListFragment as Fragment)
                        .commitAllowingStateLoss()
            }
        }
    }

    class ResultItemFactory<in T : IResultRow>(val mPreset: SearchPallet.ResultRow) : GenericHolderFactory<T>() {
        override fun getViewHolder(view: ViewGroup): GenericViewHolder<T> {
            return ResultViewHolder(LayoutInflater.from(view.context).inflate(R.layout.rsc_result_row, view, false), mPreset)
        }
    }
}
