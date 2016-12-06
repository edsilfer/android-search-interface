package br.com.edsilfer.android.search_interface.model.intf

import br.com.edsilfer.android.chipinterface.model.Chip

/**
 * Created by efernandes on 09/11/16.
 */

interface ISearchBarManager<T> {

    fun getSearchWithNoSpans(): String

    fun getSearchWithSpans(): String

    fun setSearch(query: String)

    fun addChip(item : IResultRow, replaceable : String)

    fun removeChip(item : IResultRow)

    fun getChips() : Set<Chip>
}
