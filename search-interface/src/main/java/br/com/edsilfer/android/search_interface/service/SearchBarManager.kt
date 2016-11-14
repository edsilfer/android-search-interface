package br.com.edsilfer.android.search_interface.service

import android.content.res.ColorStateList
import android.support.annotation.ColorInt
import android.support.design.widget.TextInputLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import br.com.edsilfer.android.search_interface.R
import br.com.edsilfer.android.search_interface.model.ISearchBarManager
import br.com.edsilfer.android.search_interface.model.SearchPallet
import br.com.edsilfer.android.search_interface.model.enum.Events
import br.com.edsilfer.kotlin_support.extensions.hideIndeterminateProgressBar
import br.com.edsilfer.kotlin_support.extensions.log
import br.com.edsilfer.kotlin_support.extensions.showIndeterminateProgressBar
import com.google.common.base.Strings
import org.jetbrains.anko.hintTextColor


/**
 * Created by efernandes on 09/11/16.
 */

class SearchBarManager private constructor(val mActivity: AppCompatActivity, val mPreset: SearchPallet.SearchBar) : ISearchBarManager {

    private val mInput: EditText
    //private val mInputWrapper: TextInputLayout
    private val mBack: ImageView
    private val mClear: ImageView
    private val mToolbar: Toolbar

    companion object {
        fun getInstance(activity: AppCompatActivity, preset: SearchPallet.SearchBar): SearchBarManager {
            return SearchBarManager(activity, preset)
        }

    }

    init {
        //mInputWrapper = mActivity.findViewById(R.id.input_wrapper) as TextInputLayout
        mBack = mActivity.findViewById(R.id.back) as ImageView
        mToolbar = mActivity.findViewById(R.id.search_toolbar) as Toolbar
        //mInput = mActivity.findViewById(R.id.input) as EditText

        mToolbar.setBackgroundColor(mActivity.resources.getColor(mPreset.colorPrimary));

        val iw = TextInputLayout(mActivity, null, R.style.SearchText)
        iw.layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 5f)
        mInput = EditText(mActivity, null, R.style.TextInputThemeDark)
        mInput.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
        mClear = ImageView(mActivity)
        mClear.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
        mClear.setImageResource(R.drawable.ic_close_black_24dp)

        iw.addView(mInput)
        iw.hint = "Type something..."
        iw.isClickable = false

        (mActivity.findViewById(R.id.toolbar_inner_container) as LinearLayout).addView(iw)
        (mActivity.findViewById(R.id.toolbar_inner_container) as LinearLayout).addView(mClear)

        addSearchTypedListener()
        addClearOnClickListener()
        addOnBackClickListener()

        mInput.requestFocus()
    }


    private fun addOnBackClickListener() {
        mBack.setImageResource(mPreset.iconBack)
        mBack.setOnClickListener {
            mActivity.finish()
        }
    }

    private fun addClearOnClickListener() {
        mClear.setImageResource(mPreset.iconClear)
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
                log("te3xt changed: ${getSearch()}")
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
        mInput.setText(query)
    }

}
