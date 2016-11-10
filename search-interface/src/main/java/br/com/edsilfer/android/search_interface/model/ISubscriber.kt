package br.com.edsilfer.android.search_interface.model

import br.com.edsilfer.android.search_interface.model.enum.Events
import java.io.Serializable

/**
 * Created by efernandes on 09/11/16.
 */

interface ISubscriber : Serializable {

    fun execute(event: Events, payload: Any)

}
