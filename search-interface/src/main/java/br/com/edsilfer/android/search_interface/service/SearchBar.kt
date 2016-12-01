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
class SearchBar(
        val mActivity: AppCompatActivity,
        val mPreset: SearchPallet.SearchBar,
        val searchType: SearchType
) : ISearchBarManager, Serializable {

    private val mInputWrapper: TextInputLayout
    private val mInput: ChipEditText
    private val mDone: Button
    private val mClear: ImageView
    private val mToolbar: Toolbar

    init {
        mInputWrapper = mActivity.findViewById(R.id.input_wrapper) as TextInputLayout
        mToolbar = mActivity.findViewById(R.id.search_toolbar) as Toolbar
        mActivity.setSupportActionBar(mToolbar)
        mToolbar.setBackgroundColor(mActivity.resources.getColor(mPreset.colorPrimary))
        mToolbar.setNavigationIcon(mPreset.iconBack)
        mToolbar.setNavigationOnClickListener {
            mActivity.finish()
        }

        mInput = mActivity.findViewById(R.id.input) as ChipEditText
        mInput.requestFocus()
        mInput.setChipStyle(Presets.preset02())
        mInputWrapper.hint = mActivity.getString(mPreset.hintText)

        mClear = mActivity.findViewById(R.id.clear) as ImageView
        mClear.setBackgroundResource(mPreset.iconClear)
        mDone = mActivity.findViewById(R.id.done) as Button

        toggleClearButtonVisibility()
        addSearchTypedListener()
        addClearOnClickListener()
        mActivity.showSoftKeyboard()
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
                    if (!Strings.isNullOrEmpty(getSearch())) mClear.visibility = ImageView.VISIBLE
                    else mClear.visibility = ImageView.INVISIBLE
                }
                NotificationCenter.notify(Events.ON_SEARCH_TYPED, mInput.getTextWithNoSpans())
            }
        })
    }

    // PUBLIC INTERFACE ============================================================================
    override fun getSearch(): String {
        return mInput.getTextWithNoSpans()
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
}
