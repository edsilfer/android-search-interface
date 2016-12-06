package br.com.edsilfer.android.sinterface.demo.util

import android.os.IBinder
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import java.lang.reflect.Method
import java.util.*

/**
 * Created by efernandes on 06/12/16.
 */

class DisableAnimationsRule : TestRule {
    private var mSetAnimationScalesMethod: Method? = null
    private var mGetAnimationScalesMethod: Method? = null
    private var mWindowManagerObject: Any? = null

    init {
        try {
            val windowManagerStubClazz = Class.forName("android.view.IWindowManager\$Stub")
            val asInterface = windowManagerStubClazz.getDeclaredMethod("asInterface", IBinder::class.java)
            val serviceManagerClazz = Class.forName("android.os.ServiceManager")
            val getService = serviceManagerClazz.getDeclaredMethod("getService", String::class.java)
            val windowManagerClazz = Class.forName("android.view.IWindowManager")
            mSetAnimationScalesMethod = windowManagerClazz.getDeclaredMethod("setAnimationScales", FloatArray::class.java)
            mGetAnimationScalesMethod = windowManagerClazz.getDeclaredMethod("getAnimationScales")
            val windowManagerBinder = getService.invoke(null, "window") as IBinder
            mWindowManagerObject = asInterface.invoke(null, windowManagerBinder)
        } catch (e: Exception) {
            throw RuntimeException("Failed to access animation methods", e)
        }

    }

    override fun apply(statement: Statement, description: Description): Statement {
        return object : Statement() {
            @Throws(Throwable::class)
            override fun evaluate() {
                setAnimationScaleFactors(0.0f)
                try {
                    statement.evaluate()
                } finally {
                    setAnimationScaleFactors(1.0f)
                }
            }
        }
    }

    @Throws(Exception::class)
    private fun setAnimationScaleFactors(scaleFactor: Float) {
        val scaleFactors = mGetAnimationScalesMethod!!.invoke(mWindowManagerObject) as FloatArray
        Arrays.fill(scaleFactors, scaleFactor)
        mSetAnimationScalesMethod!!.invoke(mWindowManagerObject, scaleFactors)
    }
}