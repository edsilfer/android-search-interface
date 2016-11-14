package br.com.edsilfer.android.search_interface.model

import br.com.edsilfer.android.search_interface.model.enum.ThumbnailStyle
import java.io.Serializable

/**
 * Created by efernandes on 11/11/16.
 */

data class SearchPallet(
        val searchBar: SearchBar,
        val resultRow: ResultRow,
        val resultDisclaimer: ResultDisclaimer,
        val background: Background
) : Serializable {

    data class SearchBar(
            val inputStyle: Int,
            val textInputLayoutStyle: Int,
            val colorPrimary: Int,
            val colorPrimaryDark: Int,
            val iconBack: Int,
            val iconClear: Int,
            val hintText: Int
    ) : Serializable

    data class ResultRow(
            val thumbnailStyle: ThumbnailStyle,
            val headerStyle: Int,
            val subHeader1Style: Int,
            val subHeader2Style: Int,
            val color: Int
    ) : Serializable

    data class ResultDisclaimer(
            val messageStyle: Int,
            val color: Int
    ) : Serializable

    data class Background(
            val color: Int,
            val drawable: Int = -1,
            val alpha: Float = 1.toFloat()
    ) : Serializable

    data class TextStyle(
            val textColor: Int,
            val textHintColor: Int,
            val textSize: Int,
            val textStyle: Int,
            val hangerColor: Int,
            val fontFamily: String
    ) : Serializable
}


