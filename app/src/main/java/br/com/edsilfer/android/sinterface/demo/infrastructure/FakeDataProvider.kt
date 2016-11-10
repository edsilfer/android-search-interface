package br.com.edsilfer.android.sinterface.demo.infrastructure

import br.com.edsilfer.android.sinterface.demo.model.Chat
import br.com.edsilfer.android.sinterface.demo.model.Message
import br.com.edsilfer.android.sinterface.demo.model.User
import java.util.*

/**
 * Created by User on 24/10/2016.
 */
object FakeDataProvider {

    fun provideChats(): ArrayList<Chat> {
        return arrayListOf(provideChat01(), provideChat02())
    }

    fun provideChat01(): Chat {
        val msg = Message()
        msg.sender = provideUser02()
        msg.content = "Do you bleed?"

        return Chat(
                currentUser = provideUser02(),
                lastMessage = msg,
                participants = mutableListOf(provideUser02(), provideUser01())
        )
    }

    fun provideChat02(): Chat {
        val msg = Message()
        msg.sender = provideUser02()
        msg.content = "How is Themyscira going?"

        return Chat(
                currentUser = provideUser02(),
                lastMessage = msg,
                participants = mutableListOf(provideUser02(), provideUser03())
        )
    }

    fun provideUser01(): User {
        val u = User()
        u.thumbnail = "http://www.superhero-therapy.com/wp-content/uploads/2015/02/christopher-reeve-superman.jpg"
        u.nickname = "superman"
        u.name = "Clark Kent"
        u.id = 0.toDouble()
        return u
    }

    fun provideUser02(): User {
        val u = User()
        u.thumbnail = "http://images1.fanpop.com/images/photos/2000000/Bruce-Wayne-the-dark-knight-2022949-366-500.jpg"
        u.nickname = "batman"
        u.name = "Bruce Wayne"
        u.id = 1.toDouble()
        return u
    }

    fun provideUser03(): User {
        val u = User()
        u.thumbnail = "https://4.bp.blogspot.com/-8HlAWXJpRuc/V0b67DGwBHI/AAAAAAAAVbA/FgaoiCoPHSUM--qdvZ21cLohsVrFert8QCLcB/s1600/Gal%2BGadot%2Bfim%2Bda%2Bgrava%25C3%25A7%25C3%25A3o.jpg"
        u.nickname = "wonder woman"
        u.name = "Diana Prince"
        u.id = 2.toDouble()
        return u
    }
}
