package br.com.edsilfer.android.search_interface.model.intf

/**
 * Created by efernandes on 09/11/16.
 */

interface ISearchBarManager {

    fun getSearch(): String

    fun setSearch(query: String)

}
