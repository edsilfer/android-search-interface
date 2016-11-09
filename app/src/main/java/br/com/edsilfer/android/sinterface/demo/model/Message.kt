package br.com.edsilfer.android.sinterface.demo.model

import br.com.edsilfer.android.sinterface.demo.model.Notification
import br.com.edsilfer.android.sinterface.demo.model.User
import br.com.edsilfer.android.sinterface.demo.model.enum.MessageDirection
import com.google.gson.Gson
import java.io.Serializable

/**
 * Created by efernandes on 10/24/16.
 */
class Message : Notification(), Serializable {

    var direction = MessageDirection.INCOME
    var sender = User()

    override fun toString(): String {
        return Gson().toJson(this)
    }

}
