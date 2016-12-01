package br.com.edsilfer.android.search_interface.model.viewholder

import android.support.v7.view.ContextThemeWrapper
import android.support.v7.widget.AppCompatCheckBox
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import br.com.edsilfer.android.lmanager.model.GenericViewHolder
import br.com.edsilfer.android.search_interface.R
import br.com.edsilfer.android.search_interface.model.SearchPallet
import br.com.edsilfer.android.search_interface.model.enum.Events
import br.com.edsilfer.android.search_interface.model.enum.SearchType
import br.com.edsilfer.android.search_interface.model.enum.ThumbnailStyle
import br.com.edsilfer.android.search_interface.model.intf.IResultRow
import br.com.edsilfer.android.search_interface.service.NotificationCenter
import br.com.edsilfer.android.search_interface.service.SearchBar
import com.google.common.base.Strings
import com.mikhaellopez.circularimageview.CircularImageView
import com.squareup.picasso.Picasso
import org.jetbrains.anko.backgroundColor

/**
 * Created by User on 09/11/2016.
 */

class ResultViewHolder<T : IResultRow>(
        rootView: View,
        val mPreset: SearchPallet.ResultRow,
        val mSearchBar: SearchBar<T>,
        val mSearchType: SearchType
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
            for (si in mSearchBar.mSelectedItems) {
                if (si.getUniqueIdentifier() == item.getUniqueIdentifier()) {
                    checkBox.isChecked = true
                } else {
                    checkBox.isChecked = false
                }
            }
        }
    }

    private fun setInformation(item: T) {
        val infoContainer = rootView.findViewById(R.id.information_container) as LinearLayout
        var header: TextView? = null
        var subheader1: TextView? = null
        var subheader2: TextView? = null

        infoContainer.removeAllViews()

        if (null != rootView.findViewById(R.id.header)) {
            header = rootView.findViewById(R.id.header) as TextView
            subheader1 = rootView.findViewById(R.id.subheader1) as TextView
            subheader2 = rootView.findViewById(R.id.subheader2) as TextView
        } else {
            header = TextView(ContextThemeWrapper(rootView.context, mPreset.headerStyle), null, 0)
            header.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            header.id = R.id.header

            subheader1 = TextView(ContextThemeWrapper(rootView.context, mPreset.subHeader1Style), null, 0)
            subheader1.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            subheader1.id = R.id.subheader1

            subheader2 = TextView(ContextThemeWrapper(rootView.context, mPreset.subHeader2Style), null, 0)
            subheader2.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            subheader2.id = R.id.subheader2
        }

        header.text = item.getHeader()
        subheader1.text = item.getSubHeader1()
        subheader2.text = item.getSubHeader2()

        infoContainer.addView(header)
        infoContainer.addView(subheader1)
        infoContainer.addView(subheader2)
    }

    private fun setBackgroundColor() {
        val background = rootView.findViewById(R.id.background)
        background.alpha = mPreset.alpha
        background.backgroundColor = rootView.resources.getColor(mPreset.color)
    }

    override fun getClickableItem(): View {
        return rootView.findViewById(R.id.wrapper)
    }

    override fun onItemClicked(item: T, index: Int) {
        val checkbox = rootView.findViewById(R.id.checkbox) as AppCompatCheckBox
        if (mSearchType == SearchType.MULTI_SELECT) {
            checkbox.isChecked = !checkbox.isChecked
            if (checkbox.isChecked) {
                mSearchBar.addSelectedItem(item)
                mSearchBar.addChip(item.getChip(), mSearchBar.getSearchWithNoSpans())
            } else {
                mSearchBar.removeSelectedItem(item)
                mSearchBar.removeChip(item.getChip())
            }
        } else {
            NotificationCenter.notify(Events.ITEM_CHOSEN, item)
        }
    }

    private fun loadThumbnail(url: String) {
        val cThumbnail = rootView.findViewById(R.id.circle_thumbnail) as CircularImageView
        val sThumbnail = rootView.findViewById(R.id.square_thumbnail) as ImageView

        when (mPreset.thumbnailStyle) {
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

