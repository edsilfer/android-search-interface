package br.com.edsilfer.android.search_interface.model

/**
 * Created by efernandes on 09/11/16.
 */

interface ISearchInterface<T> {

    fun updateResults (results : MutableList<T>)

}
