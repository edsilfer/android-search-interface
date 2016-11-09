package br.com.edsilfer.android.search_interface.model

import java.io.Serializable

/**
 * Created by efernandes on 09/11/16.
 */

interface ISearchListener : Serializable{

    fun onSearchTyped (query : String)

}
