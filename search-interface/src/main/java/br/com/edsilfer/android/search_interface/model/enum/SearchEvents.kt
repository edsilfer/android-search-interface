package br.com.edsilfer.android.search_interface.model.enum

import br.com.edsilfer.kotlin_support.model.Events

/**
 * Created by efernandes on 04/11/16.
 */

enum class SearchEvents : Events {
    UPDATE_RESULTS, ON_SEARCH_TYPED, ITEM_CHOSEN, MULTI_SELECT_FINISHED, ON_SEARCH_ACTIVITY_CLOSED
}

