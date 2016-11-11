package br.com.edsilfer.android.search_interface.service

import android.support.design.widget.TextInputLayout
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import br.com.edsilfer.android.search_interface.R
import br.com.edsilfer.android.search_interface.model.ISearchBarManager
import br.com.edsilfer.android.search_interface.model.SearchPallet
import br.com.edsilfer.android.search_interface.model.enum.Events
import br.com.edsilfer.kotlin_support.extensions.hideIndeterminateProgressBar
import br.com.edsilfer.kotlin_support.extensions.showIndeterminateProgressBar
import com.google.common.base.Strings

/**
 * Created by efernandes on 09/11/16.
 */

class SearchBarManager private constructor(val mActivity: AppCompatActivity, val mPreset: SearchPallet.SearchBar) : ISearchBarManager {

    private var mInput: TextView
    private val mInputWrapper: TextInputLayout
    private val mBack: ImageView
    private val mClear: ImageView

    companion object {
        fun getInstance(activity: AppCompatActivity, preset: SearchPallet.SearchBar): SearchBarManager {
            return SearchBarManager(activity, preset)
        }

    }

    init {
        mInputWrapper = mActivity.findViewById(R.id.input_wrapper) as TextInputLayout
        mBack = mActivity.findViewById(R.id.back) as ImageView
        mClear = mActivity.findViewById(R.id.clear) as ImageView
        mInput = TextView(mActivity, null, mPreset.inputStyle)

        addInput()

        addSearchTypedListener()
        addClearOnClickListener()
        addOnBackClickListener()
    }

    private fun addInput() {
        val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        )
        mInputWrapper.addView(mInput, params)
    }

    private fun addOnBackClickListener() {
        mBack.setOnClickListener {
            mActivity.finish()
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
                if (!Strings.isNullOrEmpty(getSearch())) mClear.visibility = ImageView.VISIBLE
                else mClear.visibility = ImageView.GONE

                SearchNotificationCenter.notify(Events.ON_SEARCH_TYPED, getSearch())
            }
        })
    }

    override fun updateUI(isSearching: Boolean) {
        if (isSearching) mActivity.showIndeterminateProgressBar()
        else mActivity.hideIndeterminateProgressBar()
    }

    override fun getSearch(): String {
        return mInput.text.toString()
    }

    override fun setSearch(query: String) {
        mInput.text = query
    }

}
