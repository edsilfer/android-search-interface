package br.com.edsilfer.android.sinterface.demo.model

import br.com.edsilfer.android.search_interface.model.`interface`.IResultRow
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
        var mHeader: String = "empty mHeader",
        val currentUser: User,
        var participants: MutableList<User> = mutableListOf<User>(),
        var mThumbnail: String = "",
        var lastMessage: Message
) : Serializable, IResultRow {

    override fun getThumbnail(): String {
        return mThumbnail
    }

    override fun getHeader(): String {
        return mHeader
    }

    override fun getSubHeader1(): String {
        return "${lastMessage.sender.nickname}: ${lastMessage.content}"
    }

    override fun getSubHeader2(): String {
        return "${participants.size} participants"
    }

    private val chatType: ChatType

    init {
        chatType = if (participants.size > 2) ChatType.GROUP else ChatType.INDIVIDUAL
        if (chatType == ChatType.INDIVIDUAL) {
            removeCurrentUser()
            mHeader = participants[0].name
            mThumbnail = participants[0].thumbnail
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
