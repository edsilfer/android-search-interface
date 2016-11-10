package br.com.edsilfer.android.search_interface.model.viewholder

import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import br.com.edsilfer.android.lmanager.model.GenericViewHolder
import br.com.edsilfer.android.search_interface.R
import br.com.edsilfer.android.search_interface.model.IResultRow
import br.com.edsilfer.android.search_interface.model.enum.Events
import br.com.edsilfer.android.search_interface.service.SearchNotificationCenter
import butterknife.bindView
import com.google.common.base.Strings
import com.mikhaellopez.circularimageview.CircularImageView
import com.squareup.picasso.Picasso

/**
 * Created by User on 09/11/2016.
 */

class ResultViewHolder<in T : IResultRow>(rootView: View) : GenericViewHolder<T>(rootView) {
    companion object {
        val THUMBNAIL_RESIZE_SIZE = 50
    }

    val wrapper: RelativeLayout by bindView(R.id.wrapper)
    val thumbnail: CircularImageView by bindView(R.id.thumbnail)
    val header: TextView by bindView(R.id.header)
    val subHeader1: TextView  by bindView(R.id.subheader1)
    val subHeader2: TextView by bindView(R.id.subheader2)

    override fun onBindViewHolder(item: T) {
        header.text = item.getHeader()
        subHeader1.text = item.getSubHeader1()
        subHeader2.text = item.getSubHeader2()
        loadThumbnail(item.getThumbnail())
    }

    override fun getClickableItem(): View {
        return wrapper
    }

    override fun onItemClicked(item: T, index: Int) {
        SearchNotificationCenter.notify(Events.ITEM_CHOSEN, item)
    }

    private fun loadThumbnail(url: String) {
        if (Strings.isNullOrEmpty(url)) {
            thumbnail.visibility = CircularImageView.GONE
        } else {
            thumbnail.visibility = CircularImageView.VISIBLE
            Picasso.with(rootView.context).load(url).resize(THUMBNAIL_RESIZE_SIZE, THUMBNAIL_RESIZE_SIZE).centerCrop().into(thumbnail)
        }
    }
}
