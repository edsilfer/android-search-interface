package br.com.edsilfer.android.search_interface.presenter.activity

import android.annotation.TargetApi
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.CardView
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import br.com.edsilfer.android.chipinterface.model.ChipEvents
import br.com.edsilfer.android.lmanager.model.CustomDataSet
import br.com.edsilfer.android.lmanager.model.GenericHolderFactory
import br.com.edsilfer.android.lmanager.model.GenericViewHolder
import br.com.edsilfer.android.lmanager.model.IListControl
import br.com.edsilfer.android.lmanager.presenter.GenericListFragment
import br.com.edsilfer.android.search_interface.R
import br.com.edsilfer.android.search_interface.model.enum.SearchEvents
import br.com.edsilfer.android.search_interface.model.enum.SearchType
import br.com.edsilfer.android.search_interface.model.enum.ThumbnailStyle
import br.com.edsilfer.android.search_interface.model.intf.IResultRow
import br.com.edsilfer.android.search_interface.model.intf.ISearchInterface
import br.com.edsilfer.android.search_interface.model.viewholder.ResultViewHolder
import br.com.edsilfer.android.search_interface.model.xml.Component
import br.com.edsilfer.android.search_interface.model.xml.SearchInterface
import br.com.edsilfer.android.search_interface.service.SearchBarManager
import br.com.edsilfer.kotlin_support.extensions.addEventSubscriber
import br.com.edsilfer.kotlin_support.extensions.hideIndeterminateProgressBar
import br.com.edsilfer.kotlin_support.extensions.paintStatusBar
import br.com.edsilfer.kotlin_support.extensions.setStyle
import br.com.edsilfer.kotlin_support.model.Events
import br.com.edsilfer.kotlin_support.model.ISubscriber
import br.com.edsilfer.kotlin_support.service.NotificationCenter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_search_dark.*
import org.jetbrains.anko.backgroundColor
import org.simpleframework.xml.core.Persister
import java.io.Serializable
import java.util.*


/**
 * Created by efernandes on 09/11/16.
 */

class ActivitySearch<T : IResultRow> : AppCompatActivity(), ISearchInterface<T>, ISubscriber {

    companion object {
        val TAG_LIST_ITEMS = "listitems"
        val ARG_SEARCH_TEMPLATE = "searchpreset"
        val SCHEMA_VALIDATOR = "schema_android_chip.xsd"
    }

    private var mListFragment: IListControl<T>? = null
    private var mSearchBar: SearchBarManager<T>? = null
    private var mLastUpdatedItems = mutableListOf<T>()
    private var mTemplate: SearchInterface? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retrievePreset()
        configureUserInterface()
        addEventSubscriber(SearchEvents.UPDATE_RESULTS, this)
        addEventSubscriber(SearchEvents.ITEM_CHOSEN, this)
    }

    override fun onPause() {
        super.onPause()
        mSearchBar!!.closerSoftKeyboard()
    }

    override fun onStop() {
        super.onStop()
        mSearchBar!!.closerSoftKeyboard()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    private fun loadContent() {
        if (mTemplate!!.getComponentByType("search-bar").type == "theme-dark")
            setContentView(R.layout.activity_search_dark)
        else
            setContentView(R.layout.activity_search_light)
    }

    override fun onDestroy() {
        super.onDestroy()
        NotificationCenter.notify(SearchEvents.ON_SEARCH_ACTIVITY_CLOSED, null)
    }

    private fun configureUserInterface() {
        loadContent()
        paintStatusBar(Color.parseColor(mTemplate!!.getComponentByType("search-bar").getColorByID("status-bar").value), false)
        mSearchBar = SearchBarManager(this, mTemplate!!.getComponentByType("search-bar"), SearchType.fromString(mTemplate!!.type)!!)
        setBackgroundPreset()
        setNoResultsDisclaimer()
        loadFragment(arrayListOf<T>())
    }

    private fun setNoResultsDisclaimer() {
        message_wrapper.setCardBackgroundColor(Color.parseColor(mTemplate!!.getComponentByType("disclaimer").getColorByID("background").value))
        val disclaimer = TextView(this)
        disclaimer.setStyle(mTemplate!!.getComponentByType("disclaimer").getTextByID("disclaimer"))
        disclaimer.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        disclaimer.text = mTemplate!!.getComponentByType("disclaimer").disclaimer
        @TargetApi(17)
        disclaimer.textAlignment = Gravity.CENTER
        message_wrapper.addView(disclaimer)
    }

    private fun setBackgroundPreset() {
        if (mTemplate!!.isBkgImage) {
            background.visibility = ImageView.VISIBLE
            val image = resources.getIdentifier(mTemplate!!.bkg, "drawable", packageName)
            Picasso.with(this).load(image).fit().centerCrop().into(background)
        } else {
            background.visibility = ImageView.GONE
            container.backgroundColor = Color.parseColor(mTemplate!!.bkg)
        }
    }

    private fun retrievePreset() {
        val filename = intent.extras.getString(ARG_SEARCH_TEMPLATE, "")
        if (filename.isEmpty()) {
            throw IllegalArgumentException("Template cannot be empty")
        }

        val typedValue = TypedValue()
        val fileID = resources.getIdentifier(filename, "raw", packageName)

        //if (XMLValidator.validateAgainstSchema(templateFile, assets.open(SCHEMA_VALIDATOR))) {
        mTemplate = Persister().read(SearchInterface::class.java, resources.openRawResource(fileID, typedValue))
                ?: throw IllegalArgumentException("Please provide a XML file containing the layout for Android Chip")
        println("Retrieved template: ${mTemplate}")

        //}
    }

    private fun loadFragment(dataSet: ArrayList<T>) {
        if (!isFinishing) {
            mListFragment = GenericListFragment.newInstance<T>(
                    CustomDataSet<T>(dataSet),
                    ResultItemFactory(
                            mTemplate!!.getComponentByType("result-row"),
                            SearchType.fromString(mTemplate!!.type)!!,
                            mSearchBar!!,
                            ThumbnailStyle.fromString(mTemplate!!.getComponentByType("result-row").display)!!
                    )

            )

            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.replaceable, mListFragment as Fragment, TAG_LIST_ITEMS)
                    .commit()
        }
    }

    /**
     * NOTIFICATION CENTER EVENT HANDLER
     */
    override fun onEventTriggered(event: Events, payload: Any?) {
        when (event) {
            SearchEvents.UPDATE_RESULTS -> {
                updateResults(payload as MutableList<T>)
            }

            ChipEvents.CHIP_REMOVED -> {
                mListFragment?.refreshDataSet()
            }

            SearchEvents.ITEM_CHOSEN -> {
                if (SearchType.fromString(mTemplate!!.type)!! == SearchType.SINGLE_SELECT) {
                    finish()
                }
            }

            else -> {
                // DO NOTHING
            }
        }
    }

    override fun updateResults(results: MutableList<T>?) {
        hideIndeterminateProgressBar()
        mLastUpdatedItems = results!!
        if (results.size == 0 && !mSearchBar!!.getSearchWithNoSpans().isEmpty())
            hideFragment()
        else
            showFragment(results)
    }

    private fun hideFragment() {
        runOnUiThread {
            replaceable.visibility = LinearLayout.GONE
            message_wrapper.visibility = CardView.VISIBLE
            supportFragmentManager.findFragmentByTag(TAG_LIST_ITEMS)?.view?.alpha = 0f
        }
    }

    private fun showFragment(results: MutableList<T>?) {
        runOnUiThread {
            if (null != mListFragment) {
                replaceable.visibility = LinearLayout.VISIBLE
                message_wrapper.visibility = CardView.GONE
                mListFragment!!.updateDataSet(results as ArrayList<T>)
                supportFragmentManager.findFragmentByTag(TAG_LIST_ITEMS)?.view?.alpha = 1f
            }
        }
    }

    class ResultItemFactory<T : IResultRow>(
            val mTemplate: Component,
            val mSearchType: SearchType,
            val mSearchBar: SearchBarManager<T>,
            val mThumbnailStyle: ThumbnailStyle
    ) : GenericHolderFactory<T>(), Serializable {

        override fun getViewHolder(view: ViewGroup): GenericViewHolder<T> {
            return ResultViewHolder(LayoutInflater.from(view.context).inflate(R.layout.rsc_result_row, view, false), mTemplate, mSearchBar, mSearchType, mThumbnailStyle)
        }
    }
}
