package br.com.edsilfer.samples.searchinterface.model

import com.google.gson.Gson
import java.io.Serializable
import java.util.*

/**
 * Created by User on 27/10/2016.
 */

open class Notification : Serializable {

    var id = 0.toDouble()
    var content = "empty notification"
    val timestamp = Date()

    override fun toString(): String {
        return Gson().toJson(this)
    }

}
