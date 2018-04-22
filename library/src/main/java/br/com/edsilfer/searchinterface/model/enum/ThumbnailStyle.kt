package br.com.edsilfer.searchinterface.model.enum

/**
 * Created by efernandes on 04/11/16.
 */

enum class ThumbnailStyle(val mValue: String) {

    SQUARE("square"), CIRCLE("circular");

    companion object {
        fun fromString(value: String): ThumbnailStyle? {
            return ThumbnailStyle.values().firstOrNull { it.mValue == value }
        }
    }
}


