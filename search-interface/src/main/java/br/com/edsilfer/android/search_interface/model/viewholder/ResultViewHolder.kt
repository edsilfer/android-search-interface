package br.com.edsilfer.android.search_interface.model.viewholder

import android.support.v7.view.ContextThemeWrapper
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import br.com.edsilfer.android.lmanager.model.GenericViewHolder
import br.com.edsilfer.android.search_interface.R
import br.com.edsilfer.android.search_interface.model.SearchPallet
import br.com.edsilfer.android.search_interface.model.enum.Events
import br.com.edsilfer.android.search_interface.model.enum.ThumbnailStyle
import br.com.edsilfer.android.search_interface.model.intf.IResultRow
import br.com.edsilfer.android.search_interface.service.NotificationCenter
import butterknife.bindView
import com.google.common.base.Strings
import com.mikhaellopez.circularimageview.CircularImageView
import com.squareup.picasso.Picasso
import org.jetbrains.anko.backgroundColor

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
    val mBackground: View by bindView(R.id.background)
    val mInfoContainer: LinearLayout by bindView(R.id.information_container)
    var mHeader: TextView? = null
    var mSubHeader1: TextView? = null
    var mSubHeader2: TextView? = null


    override fun onBindViewHolder(item: T) {
        mInfoContainer.removeAllViews()

        if (null != rootView.findViewById(R.id.header)) {
            mHeader = rootView.findViewById(R.id.header) as TextView
            mSubHeader1 = rootView.findViewById(R.id.subheader1) as TextView
            mSubHeader2 = rootView.findViewById(R.id.subheader2) as TextView
        } else {
            createInformation()
        }

        mHeader!!.text = item.getHeader()
        mSubHeader1!!.text = item.getSubHeader1()
        mSubHeader2!!.text = item.getSubHeader2()

        mInfoContainer.addView(mHeader)
        mInfoContainer.addView(mSubHeader1)
        mInfoContainer.addView(mSubHeader2)

        mBackground.alpha = mPreset.alpha
        mBackground.backgroundColor = rootView.resources.getColor(mPreset.color)

        loadThumbnail(item.getThumbnail())
    }

    private fun createInformation() {
        mHeader = TextView(ContextThemeWrapper(rootView.context, mPreset.headerStyle), null, 0)
        mHeader!!.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        mHeader!!.id = R.id.header

        mSubHeader1 = TextView(ContextThemeWrapper(rootView.context, mPreset.subHeader1Style), null, 0)
        mSubHeader1!!.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        mSubHeader1!!.id = R.id.subheader1

        mSubHeader2 = TextView(ContextThemeWrapper(rootView.context, mPreset.subHeader2Style), null, 0)
        mSubHeader2!!.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        mSubHeader2!!.id = R.id.subheader2
    }

    override fun getClickableItem(): View {
        return mWrapper
    }

    override fun onItemClicked(item: T, index: Int) {
        NotificationCenter.notify(Events.ITEM_CHOSEN, item)
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
