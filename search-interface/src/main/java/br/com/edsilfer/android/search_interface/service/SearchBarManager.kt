package br.com.edsilfer.android.search_interface.service

import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.widget.ImageView
import android.widget.TextView
import br.com.edsilfer.android.search_interface.R
import br.com.edsilfer.android.search_interface.model.ISearchBarManager
import br.com.edsilfer.android.search_interface.model.ISearchListener
import br.com.edsilfer.kotlin_support.extensions.hideIndeterminateProgressBar
import br.com.edsilfer.kotlin_support.extensions.showIndeterminateProgressBar
import com.google.common.base.Strings

/**
 * Created by efernandes on 09/11/16.
 */

class SearchBarManager private constructor(val mActivity: AppCompatActivity, val mSearchListener: ISearchListener) : ISearchBarManager {

    private val mInput: TextView
    private val mBack: ImageView
    private val mClear: ImageView

    companion object {
        fun getInstance(activity: AppCompatActivity, listener: ISearchListener): SearchBarManager {
            return SearchBarManager(activity, listener)
        }
    }

    init {
        mInput = mActivity.findViewById(R.id.input) as TextView
        mBack = mActivity.findViewById(R.id.back) as ImageView
        mClear = mActivity.findViewById(R.id.clear) as ImageView
        addSearchTypedListener()
        addClearOnClickListener()
        addOnBackClickListener()
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
                mSearchListener.onSearchTyped(getSearch())
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
