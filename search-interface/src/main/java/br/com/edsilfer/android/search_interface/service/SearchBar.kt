package br.com.edsilfer.android.search_interface.service

import android.support.design.widget.TextInputLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import br.com.edsilfer.android.chipinterface.model.Chip
import br.com.edsilfer.android.chipinterface.model.Presets
import br.com.edsilfer.android.chipinterface.presenter.ChipEditText
import br.com.edsilfer.android.search_interface.R
import br.com.edsilfer.android.search_interface.model.SearchPallet
import br.com.edsilfer.android.search_interface.model.enum.Events
import br.com.edsilfer.android.search_interface.model.enum.SearchType
import br.com.edsilfer.android.search_interface.model.intf.ISearchBarManager
import br.com.edsilfer.kotlin_support.extensions.showIndeterminateProgressBar
import br.com.edsilfer.kotlin_support.extensions.showSoftKeyboard
import com.google.common.base.Strings
import java.io.Serializable


/**
 * Created by efernandes on 09/11/16.
 */
class SearchBar<T>(
        val mActivity: AppCompatActivity,
        val mPreset: SearchPallet.SearchBar,
        val searchType: SearchType
) : ISearchBarManager<T>, Serializable {

    private val mInputWrapper: TextInputLayout
    private val mInput: ChipEditText
    private val mDone: Button
    private val mClear: ImageView
    private val mToolbar: Toolbar

    var mSelectedItems = mutableListOf<T>()
        private set

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
            mActivity.finish()
        }
        mActivity.setSupportActionBar(mToolbar)
        mActivity.supportActionBar!!.setDisplayShowTitleEnabled(false)
        //mActivity.supportActionBar!!.setDisplayHomeAsUpEnabled(true)
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
            NotificationCenter.notify(Events.MULTI_SELECT_FINISHED, mSelectedItems)
        }
    }

    private fun addSearchTypedListener() {
        mInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                // DO NOTHING
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // DO NOTHING
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                mActivity.showIndeterminateProgressBar(mPreset.colorLoading)

                if (searchType == SearchType.SINGLE_SELECT) {
                    if (!Strings.isNullOrEmpty(getSearchWithNoSpans())) mClear.visibility = ImageView.VISIBLE
                    else mClear.visibility = ImageView.INVISIBLE
                } else {
                    if (!Strings.isNullOrEmpty(getSearchWithSpans())) mDone.visibility = ImageView.VISIBLE
                    else mDone.visibility = Button.INVISIBLE
                }

                NotificationCenter.notify(Events.ON_SEARCH_TYPED, mInput.getTextWithNoSpans())
            }
        })
    }

    // PUBLIC INTERFACE ============================================================================
    override fun getSearchWithNoSpans(): String {
        return mInput.getTextWithNoSpans()
    }

    override fun getSearchWithSpans(): String {
        return mInput.text.toString()
    }

    override fun setSearch(query: String) {
        mInput.setText(query)
    }

    override fun addChip(chip: Chip, replaceable: String) {
        mInput.addChip(chip, replaceable)
    }

    override fun removeChip(chip: Chip) {
        mInput.removeChip(chip)
    }

    override fun addSelectedItem(item: T) {
        mSelectedItems.add(item)
    }

    override fun removeSelectedItem(item: T) {
        mSelectedItems.remove(item)
    }
}
