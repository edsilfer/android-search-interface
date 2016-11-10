package br.com.edsilfer.android.search_interface.model

import java.io.Serializable

/**
 * Created by User on 09/11/2016.
 */

interface IResultRow : Serializable {

    fun getThumbnail(): String

    fun getHeader(): String

    fun getSubHeader1(): String

    fun getSubHeader2(): String

}
