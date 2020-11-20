package com.ctl.skin_lib

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import com.ctl.skin_lib.util.SkinUtil
import java.lang.reflect.Constructor
import java.util.*
import kotlin.collections.HashMap

/**
 * created by : chentl
 * Date: 2020/11/19
 */
class SkinLayoutInflaterFactory(private val activity: Activity) :
    LayoutInflater.Factory2, Observer {

    private var skinAttribute: SkinAttribute? = null

    private val mClassPrefixList =
        listOf(
            "android.widget.",
            "android.webkit.",
            "android.app.",
            "android.view."
        )
    private val mConstructorSignature = arrayOf<Class<*>>(
        Context::class.java,
        AttributeSet::class.java
    )
    private val mConstructorMap = HashMap<String, Constructor<out View?>>()

    init {
        skinAttribute = SkinAttribute()
    }

    override fun onCreateView(
        parent: View?,
        name: String,
        context: Context,
        attrs: AttributeSet
    ): View? {
        var view = createMyView(name, context, attrs)
        if (view == null) {
            view = createView(name, context, attrs)
        }
        if (null != view) {
            skinAttribute?.lookAction(view, attrs)
        }
        return null
    }

    private fun createMyView(name: String, context: Context, attrs: AttributeSet): View? {
        if (-1 != name.indexOf(".")) {
            return null
        }
        for (element in mClassPrefixList) {
            val view = createView(element + name, context, attrs)
            if (view != null) {
                return view
            }
        }
        return null
    }

    private fun createView(name: String, context: Context, attrs: AttributeSet): View? {
        val constructor = findConstructor(context, name)
        try {
            return constructor?.newInstance(context, attrs)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        return null
    }

    override fun update(o: Observable?, arg: Any?) {
        SkinUtil.updateStatusBarColor(activity)
        skinAttribute?.changeSkin()
    }

    private fun findConstructor(context: Context, name: String): Constructor<out View?>? {
        var constructor = mConstructorMap[name]
        if (constructor == null) {
            try {
                val clazz = context.classLoader.loadClass(name).asSubclass(View::class.java)
                constructor = clazz.getConstructor(*mConstructorSignature)
                mConstructorMap[name] = constructor
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return constructor
    }
}