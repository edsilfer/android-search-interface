package br.com.edsilfer.samples.searchinterface.services

import br.com.edsilfer.kotlin_support.extensions.addEventSubscriber
import br.com.edsilfer.kotlin_support.extensions.log
import br.com.edsilfer.kotlin_support.model.Events
import br.com.edsilfer.kotlin_support.model.ISubscriber
import br.com.edsilfer.kotlin_support.service.NotificationCenter
import br.com.edsilfer.samples.searchinterface.infrastructure.FakeDataProvider
import br.com.edsilfer.samples.searchinterface.model.Chat
import br.com.edsilfer.searchinterface.model.enum.SearchEvents
import com.google.common.base.Strings
import org.jetbrains.anko.doAsync

/**
 * Created by User on 05/12/2016.
 */

class SearchManager : ISubscriber {

    init {
        addEventSubscriber(SearchEvents.ON_SEARCH_TYPED, this)
        addEventSubscriber(SearchEvents.ITEM_CHOSEN, this)
        addEventSubscriber(SearchEvents.MULTI_SELECT_FINISHED, this)
    }


    override fun onEventTriggered(event: Events, payload: Any?) {
        when (event) {
            SearchEvents.ON_SEARCH_TYPED -> performSearch(payload as String)
            SearchEvents.ITEM_CHOSEN -> log("Item: $payload has been chosen")
            SearchEvents.MULTI_SELECT_FINISHED -> {
                for (c in (payload as List<Chat>)) {
                    log("Chosen items are: ${c.getHeader()}")
                }
            }
            else -> {
                /*DO NOTHING*/
            }
        }
    }

    fun performSearch(query: String) {
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
