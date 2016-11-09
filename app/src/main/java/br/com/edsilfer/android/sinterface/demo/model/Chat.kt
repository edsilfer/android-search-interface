package br.com.edsilfer.android.sinterface.demo.model

import br.com.edsilfer.android.sinterface.demo.model.Message
import br.com.edsilfer.android.sinterface.demo.model.User
import br.com.edsilfer.android.sinterface.demo.model.enum.ChatType
import com.google.gson.Gson
import java.io.Serializable

/**
 * Created by efernandes on 04/11/16.
 */

class Chat(
        var id: Double = -1.toDouble(),
        var header: String = "empty header",
        val currentUser: User,
        var participants: MutableList<User> = mutableListOf<User>(),
        var unreadMessages: Int = 0,
        var thumbnail: String = "",
        var lastMessage: Message = Message()
) : Serializable {
    private val chatType: ChatType

    init {
        chatType = if (participants.size > 2) ChatType.GROUP else ChatType.INDIVIDUAL
        if (chatType == ChatType.INDIVIDUAL) {
            removeCurrentUser()
            header = participants[0].name
            thumbnail = participants[0].thumbnail
        }

    }

    private fun removeCurrentUser() {
        val iterator = participants.iterator()
        while (iterator.hasNext()) {
            val user = iterator.next()
            if (user.id == currentUser.id) {
                iterator.remove()
            }
        }
    }

    override fun toString(): String {
        return Gson().toJson(this)
    }
}
