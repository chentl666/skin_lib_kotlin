package com.ctl.skin_lib.util

import android.app.Activity
import android.content.Context
import android.os.Build
import com.ctl.skin_lib.SkinResources

/**
 * created by : chentl
 * Date: 2020/11/19
 */
object SkinUtil {

    private val APPCOMPAT_COLOR_PRIMARY_DARK_ATTRS =
        intArrayOf(androidx.appcompat.R.attr.colorPrimaryDark)
    private val STATUSBAR_COLOR_ATTRS =
        intArrayOf(android.R.attr.statusBarColor, android.R.attr.navigationBarColor)

    fun getResId(context: Context, attrs: IntArray): Array<Int?> {
        val resIds = arrayOfNulls<Int>(attrs.size)
        val a = context.obtainStyledAttributes(attrs)
        for (i in attrs.indices) {
            resIds[i] = a.getResourceId(i, 0)
        }
        a.recycle()
        return resIds
    }

    fun updateStatusBarColor(activity: Activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return
        }
        val resIds = getResId(activity, STATUSBAR_COLOR_ATTRS)
        val statusBarColorResId = resIds[0]
        val navigationBarColorResId = resIds[1]
        if (statusBarColorResId != null && statusBarColorResId != 0) {
            val color = SkinResources.getInstance().getColor(statusBarColorResId) ?: return
            activity.window.statusBarColor = color
        } else {
            val colorPrimaryDarkResId = getResId(activity, APPCOMPAT_COLOR_PRIMARY_DARK_ATTRS)[0]
            if (colorPrimaryDarkResId != null && colorPrimaryDarkResId != 0) {
                val color = SkinResources.getInstance().getColor(colorPrimaryDarkResId) ?: return
                activity.window.statusBarColor = color
            }
        }
        if (navigationBarColorResId != null && navigationBarColorResId != 0) {
            val color = SkinResources.getInstance().getColor(navigationBarColorResId) ?: return
            activity.window.navigationBarColor = color
        }
    }
}