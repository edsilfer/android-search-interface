package br.com.edsilfer.searchinterface.model.viewholder

import android.graphics.Color
import android.support.v7.widget.AppCompatCheckBox
import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import br.com.edsilfer.android.lmanager.model.GenericViewHolder
import br.com.edsilfer.kotlin_support.extensions.notifySubscribers
import br.com.edsilfer.kotlin_support.extensions.setColor
import br.com.edsilfer.kotlin_support.extensions.setStyle
import br.com.edsilfer.searchinterface.R
import br.com.edsilfer.searchinterface.model.enum.SearchEvents
import br.com.edsilfer.searchinterface.model.enum.SearchType
import br.com.edsilfer.searchinterface.model.enum.ThumbnailStyle
import br.com.edsilfer.searchinterface.model.intf.IResultRow
import br.com.edsilfer.searchinterface.model.xml.Component
import br.com.edsilfer.searchinterface.service.SearchBarManager
import com.google.common.base.Strings
import com.mikhaellopez.circularimageview.CircularImageView
import com.squareup.picasso.Picasso
import org.jetbrains.anko.backgroundColor


/**
 * Created by User on 09/11/2016.
 */

class ResultViewHolder<T : IResultRow>(
        rootView: View,
        val mTemplate: Component,
        val mSearchBar: SearchBarManager<T>,
        val mSearchType: SearchType,
        val mThumbnailStyle: ThumbnailStyle
) : GenericViewHolder<T>(rootView) {

    companion object {
        val THUMBNAIL_RESIZE_SIZE = 100
    }

    override fun onBindViewHolder(item: T) {
        setInformation(item)
        setBackgroundColor()
        setCheckbox(item)
        loadThumbnail(item.getThumbnail())
    }

    private fun setCheckbox(item: T) {
        val checkBox = rootView.findViewById(R.id.checkbox) as AppCompatCheckBox
        if (mSearchType == SearchType.MULTI_SELECT) {
            checkBox.setColor(Color.parseColor(mTemplate.getColorByID("checkbox").value))
            for (si in mSearchBar.getChips()) {
                if (si == item.getChip()) {
                    checkBox.isChecked = true
                    break
                } else {
                    checkBox.isChecked = false
                }
            }
        } else {
            checkBox.visibility = CheckBox.GONE
        }
    }

    private fun setInformation(item: T) {
        val infoContainer = rootView.findViewById(R.id.information_container) as LinearLayout
        val header: TextView?
        val subheader1: TextView?
        val subheader2: TextView?

        infoContainer.removeAllViews()

        if (null != rootView.findViewById(R.id.header)) {
            header = rootView.findViewById(R.id.header) as TextView
            subheader1 = rootView.findViewById(R.id.subheader1) as TextView
            subheader2 = rootView.findViewById(R.id.subheader2) as TextView
        } else {
            header = TextView(rootView.context)
            header.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            header.id = R.id.header
            header.setStyle(mTemplate.getTextByID("header"))

            subheader1 = TextView(rootView.context)
            subheader1.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            subheader1.id = R.id.subheader1
            subheader1.setStyle(mTemplate.getTextByID("subheader1"))

            subheader2 = TextView(rootView.context)
            subheader2.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            subheader2.id = R.id.subheader2
            subheader2.setStyle(mTemplate.getTextByID("subheader2"))
        }

        header.text = item.getHeader()
        subheader1.text = item.getSubHeader1()
        subheader2.text = item.getSubHeader2()

        infoContainer.addView(header)
        infoContainer.addView(subheader1)
        infoContainer.addView(subheader2)
    }

    private fun setBackgroundColor() {
        val background = rootView.findViewById<View>(R.id.background)
        background.alpha = mTemplate.getColorByID("background").alpha
        background.backgroundColor = Color.parseColor(mTemplate.getColorByID("background").value)
    }

    override fun getClickableItem(): View {
        return rootView.findViewById(R.id.wrapper)
    }

    override fun onItemClicked(item: T, index: Int) {
        val checkbox = rootView.findViewById(R.id.checkbox) as AppCompatCheckBox
        if (mSearchType == SearchType.MULTI_SELECT) {
            checkbox.isChecked = !checkbox.isChecked
            if (checkbox.isChecked) {
                mSearchBar.addChip(item, mSearchBar.getSearchWithNoSpans())
            } else {
                mSearchBar.removeChip(item)
            }
        } else {
            notifySubscribers(SearchEvents.ITEM_CHOSEN, item)
        }
    }

    private fun loadThumbnail(url: String) {
        val cThumbnail = rootView.findViewById(R.id.circle_thumbnail) as CircularImageView
        val sThumbnail = rootView.findViewById(R.id.square_thumbnail) as ImageView

        when (mThumbnailStyle) {
            ThumbnailStyle.SQUARE -> {
                cThumbnail.visibility = CircularImageView.GONE
                sThumbnail.visibility = ImageView.VISIBLE

                if (Strings.isNullOrEmpty(url)) {
                    // TODO: REPLACE BY NO IMAGE FOUND RESOURCE
                    sThumbnail.visibility = CircularImageView.GONE
                } else {
                    Picasso.with(rootView.context).load(url).resize(THUMBNAIL_RESIZE_SIZE, THUMBNAIL_RESIZE_SIZE).centerCrop().into(sThumbnail)
                }
            }
            ThumbnailStyle.CIRCLE -> {
                sThumbnail.visibility = ImageView.GONE
                cThumbnail.visibility = CircularImageView.VISIBLE

                if (Strings.isNullOrEmpty(url)) {
                    // TODO: REPLACE BY NO IMAGE FOUND RESOURCE
                    cThumbnail.visibility = CircularImageView.GONE
                } else {
                    Picasso.with(rootView.context).load(url).resize(THUMBNAIL_RESIZE_SIZE, THUMBNAIL_RESIZE_SIZE).centerCrop().into(cThumbnail)
                }
            }
        }
    }
}

