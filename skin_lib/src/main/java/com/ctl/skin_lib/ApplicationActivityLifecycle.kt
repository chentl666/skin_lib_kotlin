package com.ctl.skin_lib

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.ArrayMap
import android.view.LayoutInflater
import androidx.core.view.LayoutInflaterCompat
import com.ctl.skin_lib.util.SkinUtil
import java.util.*

/**
 * created by : chentl
 * Date: 2020/11/19
 */
class ApplicationActivityLifecycle(private val observable: Observable) :
    Application.ActivityLifecycleCallbacks {

    private val mLayoutInflaterFactories = ArrayMap<Activity, SkinLayoutInflaterFactory>()

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        SkinUtil.updateStatusBarColor(activity)

        val layoutInflater = activity.layoutInflater
        try {
            val field = LayoutInflater::class.java.getDeclaredField("mFactorySet")
            field.isAccessible = true
            field.setBoolean(layoutInflater, false)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        val skinLayoutInflaterFactory = SkinLayoutInflaterFactory(activity)
        LayoutInflaterCompat.setFactory2(layoutInflater, skinLayoutInflaterFactory)
        mLayoutInflaterFactories[activity] = skinLayoutInflaterFactory

        observable.addObserver(skinLayoutInflaterFactory)
    }

    override fun onActivityStarted(activity: Activity) {
    }

    override fun onActivityResumed(activity: Activity) {
    }

    override fun onActivityPaused(activity: Activity) {
    }

    override fun onActivityStopped(activity: Activity) {
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
    }

    override fun onActivityDestroyed(activity: Activity) {
        val observable = mLayoutInflaterFactories.remove(activity)
        SkinAction.getInstance().deleteObserver(observable)
    }
}