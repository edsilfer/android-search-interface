package br.com.edsilfer.android.search_interface.service

import br.com.edsilfer.android.search_interface.model.enum.Events
import br.com.edsilfer.android.search_interface.model.intf.ISubscriber

/**
 * Created by User on 09/11/2016.
 */

object NotificationCenter {
    private val mSubscribers = mutableMapOf<Events, MutableList<ISubscriber>>()

    fun notify(event: Events, payload: Any) {
        if (null != mSubscribers[event]) {
            for (s in mSubscribers[event]!!) {
                s.execute(event, payload)
            }
        }
    }

    fun subscribe(event: Events, subscriber: ISubscriber) {
        if (null != mSubscribers[event]) mSubscribers[event]!!.add(subscriber)
        else mSubscribers.put(event, mutableListOf(subscriber))
    }

    fun unsubscribe(event: Events, subscriber: ISubscriber) {
        mSubscribers[event]?.remove(subscriber)
    }
}
