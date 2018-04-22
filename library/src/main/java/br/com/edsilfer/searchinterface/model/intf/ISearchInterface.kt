package br.com.edsilfer.searchinterface.model.intf

/**
 * Created by efernandes on 09/11/16.
 */

interface ISearchInterface<T> {

    fun updateResults (results : MutableList<T>?)

}
