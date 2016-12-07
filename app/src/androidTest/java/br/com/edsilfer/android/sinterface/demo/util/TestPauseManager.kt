package br.com.edsilfer.android.sinterface.demo.util

import android.support.test.espresso.Espresso
import android.support.test.espresso.IdlingPolicies
import java.util.concurrent.TimeUnit

/**
 * Created by User on 06/12/2016.
 */

object TestPauseManager {
    private val TIME_OUT = 10000L

    private val mIdleResources = mutableListOf<ElapsedTimeIdlingResource>()

    fun setWaitingPolice() {
        IdlingPolicies.setMasterPolicyTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
        IdlingPolicies.setIdlingResourceTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
    }

    fun addPause(res: ElapsedTimeIdlingResource) {
        mIdleResources.add(res)
        Espresso.registerIdlingResources(res)
    }

    fun removePauses() {
        mIdleResources.forEach {
            Espresso.unregisterIdlingResources(it)
        }
        mIdleResources.clear()
    }
}
