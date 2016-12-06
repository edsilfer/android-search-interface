package br.com.edsilfer.android.search_interface.service

import android.support.design.widget.TextInputLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import br.com.edsilfer.android.chipinterface.model.Chip
import br.com.edsilfer.android.chipinterface.model.ChipEvents
import br.com.edsilfer.android.chipinterface.model.Presets
import br.com.edsilfer.android.chipinterface.presenter.ChipEditText
import br.com.edsilfer.android.search_interface.R
import br.com.edsilfer.android.search_interface.model.SearchPallet
import br.com.edsilfer.android.search_interface.model.enum.SearchEvents
import br.com.edsilfer.android.search_interface.model.enum.SearchType
import br.com.edsilfer.android.search_interface.model.intf.IResultRow
import br.com.edsilfer.android.search_interface.model.intf.ISearchBarManager
import br.com.edsilfer.kotlin_support.extensions.log
import br.com.edsilfer.kotlin_support.extensions.notifySubscribers
import br.com.edsilfer.kotlin_support.extensions.showIndeterminateProgressBar
import br.com.edsilfer.kotlin_support.extensions.showSoftKeyboard
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
        val mPreset: SearchPallet.SearchBar,
        val searchType: SearchType
) : ISearchBarManager<T>, ISubscriber, Serializable {

    private val mInputWrapper: TextInputLayout
    private val mInput: ChipEditText
    private val mDone: Button
    private val mClear: ImageView
    private val mToolbar: Toolbar
    private var mSelectedItems = mutableListOf<IResultRow>()

    init {
        mInputWrapper = mActivity.findViewById(R.id.input_wrapper) as TextInputLayout
        mToolbar = mActivity.findViewById(R.id.search_toolbar) as Toolbar
        mInput = mActivity.findViewById(R.id.input) as ChipEditText
        mDone = mActivity.findViewById(R.id.done) as Button
        mClear = mActivity.findViewById(R.id.clear) as ImageView

        setToolbarUI()
        setInputUI()

        addSearchTypedListener()
        addClearOnClickListener()
        addDoneOnClickListener()

        mActivity.showSoftKeyboard()
    }

    private fun setInputUI() {
        mInput.requestFocus()
        mInput.setChipStyle(Presets.preset02())
        mInputWrapper.hint = mActivity.getString(mPreset.hintText)
    }

    private fun setToolbarUI() {
        mToolbar.setBackgroundColor(mActivity.resources.getColor(mPreset.colorPrimary))
        mToolbar.setNavigationIcon(mPreset.iconBack)
        mToolbar.contentInsetStartWithNavigation = 0
        mClear.setBackgroundResource(mPreset.iconClear)
        toggleClearButtonVisibility()
        mToolbar.setNavigationOnClickListener {
            log("entrei no navigation click listener")
            mActivity.finish()
        }
        mActivity.setSupportActionBar(mToolbar)
        mActivity.supportActionBar!!.setDisplayShowTitleEnabled(false)
        mActivity.supportActionBar!!.setDisplayHomeAsUpEnabled(true)
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
                mActivity.showIndeterminateProgressBar(mPreset.colorLoading)

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
