package com.ctl.skin_lib

import android.content.Context
import android.content.SharedPreferences

/**
 * created by : chentl
 * Date: 2020/11/20
 */
class SkinPreference(context: Context) {

    private var mPerf: SharedPreferences? = null

    init {
        mPerf = context.getSharedPreferences("skin", Context.MODE_PRIVATE)
    }

    companion object {
        @Volatile
        private var instance: SkinPreference? = null

        fun init(context: Context): SkinPreference {
            if (instance == null) {
                synchronized(this) {
                    if (instance == null) {
                        instance = SkinPreference(context.applicationContext)
                    }
                }
            }
            return instance!!
        }

        fun getInstance(): SkinPreference {
            return instance!!
        }
    }

    fun setSkin(skinPath: String) {
        mPerf?.edit()?.putString("skin_path", skinPath)?.apply()
    }

    fun reset() {
        mPerf?.edit()?.remove("skin_path")?.apply()
    }

    fun getSkin(): String? {
        return mPerf?.getString("skin_path", null)
    }
}