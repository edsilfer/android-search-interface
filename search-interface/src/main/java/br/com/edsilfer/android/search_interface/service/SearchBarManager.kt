package br.com.edsilfer.android.search_interface.service

import android.support.design.widget.TextInputLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.view.ContextThemeWrapper
import android.support.v7.widget.Toolbar
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.widget.EditText
import android.widget.ImageButton
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
import org.jetbrains.anko.image


/**
 * Created by efernandes on 09/11/16.
 */
class SearchBarManager(val mActivity: AppCompatActivity, val mPreset: SearchPallet.SearchBar) : ISearchBarManager {

    private val mInput: EditText
    private val mBack: ImageButton
    private val mClear: ImageButton

    init {
        mBack = ImageButton(mActivity)
        mInput = EditText(ContextThemeWrapper(mActivity, mPreset.inputStyle))
        mClear = ImageButton(mActivity)
        assembleLayout()
        addSearchTypedListener()
        addClearOnClickListener()
        addOnBackClickListener()

    }

    private fun assembleLayout() {
        val container = LinearLayout(mActivity)
        val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
        container.layoutParams = params
        container.weightSum = 7f
        container.gravity = Gravity.CENTER_VERTICAL
        container.orientation = LinearLayout.HORIZONTAL

        mBack.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
        mBack.image = mActivity.getDrawable(mPreset.iconBack)
        mBack.background = mActivity.getDrawable(R.drawable.rsc_rounded_corners)

        val iw = TextInputLayout(ContextThemeWrapper(mActivity, mPreset.textInputLayoutStyle))
        iw.layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 5f)

        mInput.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 5f)
        mInput.background = null

        iw.addView(mInput)
        iw.hint = mActivity.getString(mPreset.hintText)

        mClear.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
        mClear.image = mActivity.getDrawable(mPreset.iconClear)
        mClear.background = mActivity.getDrawable(R.drawable.rsc_rounded_corners)

        val toolbar = mActivity.findViewById(R.id.search_toolbar) as Toolbar
        toolbar.setBackgroundColor(mActivity.resources.getColor(mPreset.colorPrimary))

        container.addView(mBack)
        container.addView(iw)
        container.addView(mClear)

        (mActivity.findViewById(R.id.search_toolbar) as Toolbar).addView(container)

        mInput.requestFocus()
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
                mActivity.runOnUiThread {
                    mActivity.showIndeterminateProgressBar()
                }
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
