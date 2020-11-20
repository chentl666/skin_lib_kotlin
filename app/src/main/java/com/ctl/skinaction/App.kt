package com.ctl.skinaction

import android.app.Application
import com.ctl.skin_lib.SkinAction

/**
 * created by : chentl
 * Date: 2020/11/19
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        SkinAction.initAction(this)
    }
}