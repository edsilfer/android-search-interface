package br.com.edsilfer.android.search_interface.model.viewholder

import android.support.v7.view.ContextThemeWrapper
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import br.com.edsilfer.android.lmanager.model.GenericViewHolder
import br.com.edsilfer.android.search_interface.R
import br.com.edsilfer.android.search_interface.model.IResultRow
import br.com.edsilfer.android.search_interface.model.SearchPallet
import br.com.edsilfer.android.search_interface.model.enum.Events
import br.com.edsilfer.android.search_interface.model.enum.ThumbnailStyle
import br.com.edsilfer.android.search_interface.service.SearchNotificationCenter
import butterknife.bindView
import com.google.common.base.Strings
import com.mikhaellopez.circularimageview.CircularImageView
import com.squareup.picasso.Picasso
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.textColor

/**
 * Created by User on 09/11/2016.
 */

class ResultViewHolder<in T : IResultRow>(rootView: View, val mPreset: SearchPallet.ResultRow) : GenericViewHolder<T>(rootView) {
    companion object {
        val THUMBNAIL_RESIZE_SIZE = 100
    }

    val mSquareThumbnail: ImageView by bindView(R.id.square_thumbnail)
    val mCircularThumbnail: CircularImageView by bindView(R.id.circle_thumbnail)
    val mWrapper: RelativeLayout by bindView(R.id.wrapper)
    val mInfoContainer: LinearLayout by bindView(R.id.information_container)

    override fun onBindViewHolder(item: T) {
        val header = TextView(ContextThemeWrapper(rootView.context, mPreset.headerStyle))
        header.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)

        val subHeader1 = TextView(ContextThemeWrapper(rootView.context, mPreset.subHeader1Style))
        subHeader1.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)

        val subHeader2 = TextView(ContextThemeWrapper(rootView.context, mPreset.subHeader2Style))
        subHeader2.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)

        header.text = item.getHeader()
        subHeader1.text = item.getSubHeader1()
        subHeader2.text = item.getSubHeader2()

        mInfoContainer.addView(header)
        mInfoContainer.addView(subHeader1)
        mInfoContainer.addView(subHeader2)

        mWrapper.backgroundColor = rootView.resources.getColor(mPreset.color)

        loadThumbnail(item.getThumbnail())
    }

    override fun getClickableItem(): View {
        return mWrapper
    }

    override fun onItemClicked(item: T, index: Int) {
        SearchNotificationCenter.notify(Events.ITEM_CHOSEN, item)
    }

    private fun loadThumbnail(url: String) {
        when (mPreset.thumbnailStyle) {
            ThumbnailStyle.SQUARE -> {
                mCircularThumbnail.visibility = CircularImageView.GONE
                mSquareThumbnail.visibility = ImageView.VISIBLE

                if (Strings.isNullOrEmpty(url)) {
                    // TODO: REPLACE BY NO IMAGE FOUND RESOURCE
                    mSquareThumbnail.visibility = CircularImageView.GONE
                } else {
                    Picasso.with(rootView.context).load(url).resize(THUMBNAIL_RESIZE_SIZE, THUMBNAIL_RESIZE_SIZE).centerCrop().into(mSquareThumbnail)
                }
            }
            ThumbnailStyle.CIRCLE -> {
                mSquareThumbnail.visibility = ImageView.GONE
                mCircularThumbnail.visibility = CircularImageView.VISIBLE

                if (Strings.isNullOrEmpty(url)) {
                    // TODO: REPLACE BY NO IMAGE FOUND RESOURCE
                    mCircularThumbnail.visibility = CircularImageView.GONE
                } else {
                    Picasso.with(rootView.context).load(url).resize(THUMBNAIL_RESIZE_SIZE, THUMBNAIL_RESIZE_SIZE).centerCrop().into(mCircularThumbnail)
                }
            }
        }
    }
}
