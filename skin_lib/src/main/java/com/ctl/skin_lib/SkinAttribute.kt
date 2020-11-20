package com.ctl.skin_lib

import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.ViewCompat
import com.ctl.skin_lib.util.SkinUtil

/**
 * created by : chentl
 * Date: 2020/11/19
 */
class SkinAttribute {
    private val mAttribute: List<String> = listOf(
        "background",
        "src",
        "textColor",
        "drawableLeft",
        "drawableTop",
        "drawableRight",
        "drawableBottom"
    )

    private val mSkinViews = mutableListOf<SkinView>()

    fun lookAction(view: View, attributeSet: AttributeSet) {
        val mSkinPair = mutableListOf<SkinPair>()
        for (index in 0 until attributeSet.attributeCount) {
            val attributeName = attributeSet.getAttributeName(index)
            if (mAttribute.contains(attributeName)) {
                val attributeValue = attributeSet.getAttributeValue(index)
                if (attributeValue.startsWith("#")) {
                    continue
                }
                val resId = if (attributeValue.startsWith("?")) {
                    val attrId = attributeValue.substring(1).toInt()
                    SkinUtil.getResId(view.context, intArrayOf(attrId))[0] ?: 0
                } else {
                    attributeValue.substring(1).toInt()
                }
                val skinPair = SkinPair(attributeName, resId)
                mSkinPair.add(skinPair)
            }
        }
        if (mSkinPair.isNotEmpty() || view is SkinViewSupport) {
            val skinView = SkinView(view, mSkinPair)
            skinView.changeSkin()
            mSkinViews.add(skinView)
        }
    }

    inner class SkinView(private val view: View, private val skinPairs: List<SkinPair>) {

        private fun changeSkinAction() {
            if (view is SkinViewSupport) {
                view.runSkin()
            }
        }

        fun changeSkin() {
            changeSkinAction()
            for (skinPair in skinPairs) {
                var bottom: Drawable? = null
                var right: Drawable? = null
                var top: Drawable? = null
                var left: Drawable? = null
                when (skinPair.attributeName) {
                    "background" -> {
                        val background: Any =
                            SkinResources.getInstance().getBackground(skinPair.resId)
                                ?: return
                        if (background is Int) {
                            view.setBackgroundColor(background)
                        } else {
                            ViewCompat.setBackground(view, background as Drawable)
                        }
                    }
                    "src" -> {
                        val background: Any =
                            SkinResources.getInstance().getBackground(skinPair.resId) ?: return
                        if (background is Int) {
                            (view as ImageView).setImageDrawable(ColorDrawable(background))
                        } else {
                            (view as ImageView).setImageDrawable(background as Drawable)
                        }
                    }
                    "textColor" -> {
                        (view as TextView).setTextColor(
                            SkinResources.getInstance().getColorStateList(skinPair.resId)
                        )
                    }
                    "drawableLeft" -> {
                        left = SkinResources.getInstance().getDrawable(skinPair.resId)
                    }
                    "drawableTop" -> {
                        top = SkinResources.getInstance().getDrawable(skinPair.resId)
                    }
                    "drawableRight" -> {
                        right = SkinResources.getInstance().getDrawable(skinPair.resId)
                    }
                    "drawableBottom" -> {
                        bottom = SkinResources.getInstance().getDrawable(skinPair.resId)
                    }
                }
                if (null != left || null != top || null != right || null != bottom) {
                    (view as TextView).setCompoundDrawablesWithIntrinsicBounds(
                        left,
                        top,
                        right,
                        bottom
                    )
                }
            }
        }
    }

    data class SkinPair(val attributeName: String, val resId: Int)

    fun changeSkin() {
        for (mSkinView in mSkinViews) {
            mSkinView.changeSkin()
        }
    }
}