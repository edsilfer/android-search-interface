package br.com.edsilfer.searchinterface.model.intf

import br.com.edsilfer.android.chipinterface.model.Chip
import java.io.Serializable

/**
 * Created by User on 09/11/2016.
 */

abstract class IResultRow : Serializable {

    private val mChip = object : Chip() {
        override fun getHeader(): String {
            return this@IResultRow.getHeader()
        }

        override fun getSubheader(): String {
            return this@IResultRow.getSubHeader1()
        }

        override fun getThumbnail(): String {
            return this@IResultRow.getThumbnail()
        }
    }

    abstract fun getThumbnail(): String

    abstract fun getHeader(): String

    abstract fun getSubHeader1(): String

    abstract fun getSubHeader2(): String

    fun getChip(): Chip {
        return mChip
    }
}
