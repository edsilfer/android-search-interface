package br.com.edsilfer.samples.searchinterface.model

import br.com.edsilfer.samples.searchinterface.model.enum.UserStatus
import com.google.gson.Gson
import java.io.Serializable

/**
 * Created by User on 27/10/2016.
 */

data class User(
        var id: Double = 0.toDouble(),
        var nickname: String = "unknown",
        var name: String = "Unknown User",
        var thumbnail: String = "",
        var status: UserStatus = UserStatus.OFFLINE
) : Serializable {


    override fun toString(): String {
        return Gson().toJson(this)
    }

}