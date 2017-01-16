package br.com.edsilfer.android.search_interface.service

import android.content.DialogInterface
import android.graphics.Color
import android.support.design.widget.TextInputLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import br.com.edsilfer.android.chipinterface.model.Chip
import br.com.edsilfer.android.chipinterface.model.ChipEvents
import br.com.edsilfer.android.chipinterface.presenter.ChipEditText
import br.com.edsilfer.android.search_interface.R
import br.com.edsilfer.android.search_interface.model.enum.SearchEvents
import br.com.edsilfer.android.search_interface.model.enum.SearchType
import br.com.edsilfer.android.search_interface.model.intf.IResultRow
import br.com.edsilfer.android.search_interface.model.intf.ISearchBarManager
import br.com.edsilfer.android.search_interface.model.xml.Component
import br.com.edsilfer.kotlin_support.extensions.*
import br.com.edsilfer.kotlin_support.model.Events
import br.com.edsilfer.kotlin_support.model.ISubscriber
import br.com.edsilfer.kotlin_support.service.keyboard.EnhancedTextWatcher
import com.google.common.base.Strings
import java.io.Serializable


/**
 * Created by efernandes on 09/11/16.
 */
class SearchBarManager<T>(
        val mActivity: AppCompatActivity,
        val mPreset: Component,
        val searchType: SearchType
) : ISearchBarManager<T>, ISubscriber, Serializable {

    private val mInputWrapper: TextInputLayout = mActivity.findViewById(R.id.input_wrapper) as TextInputLayout
    private val mInput: ChipEditText = mActivity.findViewById(R.id.input) as ChipEditText
    private val mDone: Button = mActivity.findViewById(R.id.done) as Button
    private val mClear: ImageView = mActivity.findViewById(R.id.clear) as ImageView
    private val mToolbar: Toolbar = mActivity.findViewById(R.id.search_toolbar) as Toolbar
    private var mSelectedItems = mutableListOf<IResultRow>()

    init {
        mInput.setStyle(mPreset.getTextByID("search-input"))

        setToolbarUI()
        setInputUI()

        addSearchTypedListener()
        addClearOnClickListener()
        addDoneOnClickListener()

        mActivity.showSoftKeyboard()
    }

    private fun setInputUI() {
        mInput.requestFocus()
        mInputWrapper.hint = mPreset.disclaimer
    }

    private fun setToolbarUI() {
        mActivity.setSupportActionBar(mToolbar)
        mActivity.supportActionBar!!.setDisplayShowTitleEnabled(false)
        mActivity.supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        mToolbar.setBackgroundColor(Color.parseColor(mPreset.getColorByID("background").value))
        // FIXME: set according to theme
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_left_white_24dp)
        mToolbar.contentInsetStartWithNavigation = 0
        // FIXME: set according to theme
        mClear.setBackgroundResource(R.drawable.ic_close_white_24dp)
        toggleClearButtonVisibility()

        mToolbar.setNavigationOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                mActivity.finish()
            }

        })
    }

    private fun toggleClearButtonVisibility() {
        if (searchType == SearchType.MULTI_SELECT) {
            mDone.visibility = Button.VISIBLE
            mClear.visibility = ImageButton.GONE
        } else {
            mDone.visibility = Button.GONE
            mClear.visibility = ImageButton.VISIBLE
        }
    }

    private fun addClearOnClickListener() {
        mClear.setOnClickListener {
            setSearch("")
        }
    }

    private fun addDoneOnClickListener() {
        mDone.setOnClickListener {
            notifySubscribers(SearchEvents.MULTI_SELECT_FINISHED, mSelectedItems)

        }
    }

    private fun addSearchTypedListener() {
        mInput.addTextChangedListener(object : EnhancedTextWatcher(mInput) {
            override fun onTextChanged(cursor: Int, isBackspace: Boolean, deletedChar: Char) {
                mActivity.showIndeterminateProgressBar(Color.parseColor(mPreset.getColorByID("loading").value), false)

                if (searchType == SearchType.SINGLE_SELECT) {
                    if (!Strings.isNullOrEmpty(getSearchWithSpans())) mClear.visibility = ImageView.VISIBLE
                    else mClear.visibility = ImageView.INVISIBLE
                } else {
                    if (!Strings.isNullOrEmpty(getSearchWithSpans())) mDone.visibility = ImageView.VISIBLE
                    else mDone.visibility = Button.INVISIBLE
                }

                notifySubscribers(SearchEvents.ON_SEARCH_TYPED, this@SearchBarManager.mInput.getTextWithNoSpans())
            }
        })
    }

    // PUBLIC INTERFACE ============================================================================
    override fun closerSoftKeyboard() {
        mInput.hideSoftKeyboard()
    }

    override fun onEventTriggered(event: Events, payload: Any?) {
        when (event) {
            ChipEvents.CHIP_ADDED -> {
                val item = getItem(payload as Chip)
                if (item != null) mSelectedItems.add(item)
            }

            ChipEvents.CHIP_REMOVED -> {
                val item = getItem(payload as Chip)
                if (item != null) mSelectedItems.remove(item)
            }

            else -> {
                // DO NOTHING
            }
        }
    }

    private fun getItem(chip: Chip): IResultRow? {
        return mSelectedItems.firstOrNull { it.getChip() == chip }
    }

    override fun getChips(): Set<Chip> {
        return ChipEditText.mChips
    }

    override fun getSearchWithNoSpans(): String {
        return mInput.getTextWithNoSpans()
    }

    override fun getSearchWithSpans(): String {
        return mInput.text.toString()
    }

    override fun setSearch(query: String) {
        mInput.setText(query)
    }

    override fun addChip(item: IResultRow, replaceable: String) {
        mInput.addChip(item.getChip(), replaceable)
        mSelectedItems.add(item)
    }

    override fun removeChip(item: IResultRow) {
        mInput.removeChip(item.getChip())
        mSelectedItems.remove(item)
    }
}
