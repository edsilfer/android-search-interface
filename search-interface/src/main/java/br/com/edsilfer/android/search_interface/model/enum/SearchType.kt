package br.com.edsilfer.android.search_interface.model.enum

/**
 * Created by efernandes on 04/11/16.
 */

enum class SearchType(val value: String) {
    SINGLE_SELECT("single-select"), MULTI_SELECT("multi-select");

    companion object {
        fun fromString(value: String): SearchType? {
            return SearchType.values().firstOrNull { it.value == value }
        }
    }

}


