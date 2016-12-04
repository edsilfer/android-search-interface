package br.com.edsilfer.android.sinterface.demo.presenter

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import br.com.edsilfer.android.chipinterface.model.Chip
import br.com.edsilfer.android.search_interface.model.enum.SearchEvents
import br.com.edsilfer.android.search_interface.presenter.activity.ActivitySearch
import br.com.edsilfer.android.sinterface.demo.infrastructure.FakeDataProvider
import br.com.edsilfer.android.sinterface.demo.model.Chat
import br.com.edsilfer.android.sinterface.demo.services.Samples
import br.com.edsilfer.kotlin_support.extensions.addEventSubscriber
import br.com.edsilfer.kotlin_support.extensions.log
import br.com.edsilfer.kotlin_support.model.Events
import br.com.edsilfer.kotlin_support.model.ISubscriber
import br.com.edsilfer.kotlin_support.service.NotificationCenter
import com.google.common.base.Strings
import org.jetbrains.anko.doAsync

/**
 * Created by efernandes on 09/11/16.
 */

class ActivityHomepage : AppCompatActivity(), ISubscriber {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startSearchActivity()
        addEventSubscriber(SearchEvents.ON_SEARCH_TYPED, this)
        addEventSubscriber(SearchEvents.ITEM_CHOSEN, this)
        addEventSubscriber(SearchEvents.MULTI_SELECT_FINISHED, this)
    }

    private fun startSearchActivity() {
        val intent = Intent(this, ActivitySearch::class.java)
        intent.putExtra(ActivitySearch.ARG_SEARCH_PRESET, Samples().sample01())
        startActivity(intent)
    }

    override fun onEventTriggered(event: Events, payload: Any?) {
        when (event) {
            SearchEvents.ON_SEARCH_TYPED -> performSearch(payload as String)
            SearchEvents.ITEM_CHOSEN -> log("Item: $payload has been chosen")
            SearchEvents.MULTI_SELECT_FINISHED -> {
                for (c in (payload as Set<Chip>)) {
                    log("Chosen items are: ${c.getHeader()}")
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
            if (Strings.isNullOrEmpty(query)) NotificationCenter.notify(SearchEvents.UPDATE_RESULTS, mutableListOf<Chat>())
            else NotificationCenter
                    .notify(
                            SearchEvents.UPDATE_RESULTS,
                            FakeDataProvider
                                    .provideChats()
                                    .filter { it.mHeader.toLowerCase().contains(query.toLowerCase()) }
                    )
        }
    }
}


