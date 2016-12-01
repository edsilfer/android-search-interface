package br.com.edsilfer.android.sinterface.demo.presenter

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import br.com.edsilfer.android.search_interface.model.enum.Events
import br.com.edsilfer.android.search_interface.model.intf.ISubscriber
import br.com.edsilfer.android.search_interface.presenter.activity.ActivitySearch
import br.com.edsilfer.android.search_interface.service.NotificationCenter
import br.com.edsilfer.android.sinterface.demo.infrastructure.FakeDataProvider
import br.com.edsilfer.android.sinterface.demo.model.Chat
import br.com.edsilfer.android.sinterface.demo.services.Samples
import br.com.edsilfer.kotlin_support.extensions.log
import com.google.common.base.Strings
import org.jetbrains.anko.doAsync

/**
 * Created by efernandes on 09/11/16.
 */

class ActivityHomepage : AppCompatActivity(), ISubscriber {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startSearchActivity()
        NotificationCenter.subscribe(Events.ON_SEARCH_TYPED, this)
        NotificationCenter.subscribe(Events.ITEM_CHOSEN, this)
        NotificationCenter.subscribe(Events.MULTI_SELECT_FINISHED, this)
    }

    private fun startSearchActivity() {
        val intent = Intent(this, ActivitySearch::class.java)
        intent.putExtra(ActivitySearch.ARG_SEARCH_PRESET, Samples().sample01())
        startActivity(intent)
    }

    override fun execute(event: Events, payload: Any) {
        when (event) {
            Events.ON_SEARCH_TYPED -> performSearch(payload as String)
            Events.ITEM_CHOSEN -> log("Item: $payload has been chosen")
            Events.MULTI_SELECT_FINISHED -> {
                for (c in (payload as MutableList<Chat>)) {
                    log("Chosen items are: ${c.mHeader}")
                }
            }
            else -> {
                /*DO NOTHING*/
            }
        }
    }

    private fun performSearch(query: String) {
        log("received query: $query")
        doAsync {
            if (Strings.isNullOrEmpty(query)) NotificationCenter.notify(Events.UPDATE_RESULTS, mutableListOf<Chat>())
            else NotificationCenter
                    .notify(
                            Events.UPDATE_RESULTS,
                            FakeDataProvider
                                    .provideChats()
                                    .filter { it.mHeader.toLowerCase().contains(query.toLowerCase()) }
                    )
        }
    }
}


