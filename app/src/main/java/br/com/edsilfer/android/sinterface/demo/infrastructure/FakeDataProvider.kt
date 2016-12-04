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
        return arrayListOf(provideChat01(), provideChat02(), provideChat03(), provideChat04(), provideChat06())
    }

    fun provideChat01(): Chat {
        val msg = Message()
        msg.sender = provideBatman()
        msg.content = "Do you bleed?"

        return Chat(
                currentUser = provideBatman(),
                lastMessage = msg,
                participants = mutableListOf(provideBatman(), provideSuperman())
        )
    }

    fun provideChat02(): Chat {
        val msg = Message()
        msg.sender = provideBatman()
        msg.content = "How is Themyscira going?"

        return Chat(
                currentUser = provideBatman(),
                lastMessage = msg,
                participants = mutableListOf(provideBatman(), provideWonderWoman())
        )
    }

    fun provideChat03(): Chat {
        val msg = Message()
        msg.sender = provideBatman()
        msg.content = "Then why do you want to kill me?"

        return Chat(
                currentUser = provideBatman(),
                lastMessage = msg,
                participants = mutableListOf(provideBatman(), provideJoker())
        )
    }

    fun provideChat04(): Chat {
        val msg = Message()
        msg.sender = provideJoker()
        msg.content = "Whos dad's little girl?"

        return Chat(
                currentUser = provideJoker(),
                lastMessage = msg,
                participants = mutableListOf(provideHarleyQuinn(), provideJoker())
        )
    }

    fun provideChat06(): Chat {
        val msg = Message()
        msg.sender = provideGreenLantern()
        msg.content = "We need to talk..."

        return Chat(
                currentUser = provideGreenLantern(),
                lastMessage = msg,
                participants = mutableListOf(provideGreenLantern(), provideBatman())
        )
    }

    fun provideSuperman(): User {
        val u = User()
        u.thumbnail = "http://www.superhero-therapy.com/wp-content/uploads/2015/02/christopher-reeve-superman.jpg"
        u.nickname = "superman"
        u.name = "Clark Kent"
        u.id = 0.toDouble()
        return u
    }

    fun provideBatman(): User {
        val u = User()
        u.thumbnail = "http://images1.fanpop.com/images/photos/2000000/Bruce-Wayne-the-dark-knight-2022949-366-500.jpg"
        u.nickname = "batman"
        u.name = "Bruce Wayne"
        u.id = 1.toDouble()
        return u
    }

    fun provideWonderWoman(): User {
        val u = User()
        u.thumbnail = "https://4.bp.blogspot.com/-8HlAWXJpRuc/V0b67DGwBHI/AAAAAAAAVbA/FgaoiCoPHSUM--qdvZ21cLohsVrFert8QCLcB/s1600/Gal%2BGadot%2Bfim%2Bda%2Bgrava%25C3%25A7%25C3%25A3o.jpg"
        u.nickname = "wonder woman"
        u.name = "Diana Prince"
        u.id = 2.toDouble()
        return u
    }

    fun provideGreenLantern(): User {
        val u = User()
        u.thumbnail = "http://www.sideshowtoy.com/wp-content/uploads/2015/10/green-lantern-premium-format-dc-comics-feature-3003921.jpg"
        u.nickname = "Green Lantern"
        u.name = "Hal Jordan"
        u.id = 3.toDouble()
        return u
    }

    fun provideFlash(): User {
        val u = User()
        u.thumbnail = "http://4.bp.blogspot.com/-nY_wXMo-4CU/VcUAhKh5B8I/AAAAAAAAAGU/jsVGjv7hiQE/s1600/the-flash.png"
        u.nickname = "The Flash"
        u.name = "Barry Allan"
        u.id = 4.toDouble()
        return u
    }

    fun provideJoker(): User {
        val u = User()
        u.thumbnail = "http://az616578.vo.msecnd.net/files/2016/08/08/6360622035014053461005076937_joker.png"
        u.nickname = "Joker"
        u.name = "???????"
        u.id = 5.toDouble()
        return u
    }

    fun provideHarleyQuinn(): User {
        val u = User()
        u.thumbnail = "http://www.themarysue.com/wp-content/uploads/2016/10/harley-quinn-robbie.jpeg"
        u.nickname = "Harley Quinn"
        u.name = "Harleen Frances Quinzel"
        u.id = 6.toDouble()
        return u
    }
}
