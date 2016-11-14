package br.com.edsilfer.android.search_interface.service

import android.graphics.Typeface
import android.widget.EditText
import br.com.edsilfer.android.search_interface.model.SearchPallet

/**
 * Created by User on 13/11/2016.
 */

object TextStyleParser {

    // TODO: move to kotlin support library
    fun setEditTextStyle(editText: EditText, style: SearchPallet.TextStyle) {
        editText.typeface = Typeface.create(style.fontFamily, Typeface.NORMAL);
        editText.setTypeface(null, style.textStyle)
        editText.setTextColor(editText.resources.getColor(style.textColor))
        //editText.setHint(editText.resources.getColor(style.textHintColor))
        editText.textSize = editText.resources.getDimension(style.textSize)
    }

}
