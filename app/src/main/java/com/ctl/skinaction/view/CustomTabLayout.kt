package com.ctl.skinaction.view

import android.content.Context
import android.util.AttributeSet
import com.ctl.skin_lib.SkinResources
import com.ctl.skin_lib.SkinViewSupport
import com.ctl.skinaction.R
import com.google.android.material.tabs.TabLayout

/**
 * created by : chentl
 * Date: 2020/11/19
 */
class CustomTabLayout : TabLayout, SkinViewSupport {

    private var tabIndicatorColorResId: Int = 0
    private var tabTextColorResId: Int = 0

    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)
    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int)
            : super(context, attributeSet, defStyleAttr) {
        val a = context.obtainStyledAttributes(attributeSet, R.styleable.TabLayout, defStyleAttr, 0)
        tabIndicatorColorResId = a.getResourceId(R.styleable.TabLayout_tabIndicatorColor, 0)
        tabTextColorResId = a.getResourceId(R.styleable.TabLayout_tabTextColor, 0)
        a.recycle()
    }

    override fun runSkin() {
        if (tabIndicatorColorResId != 0) {
            val tabIndicatorColor = SkinResources.getInstance().getColor(tabIndicatorColorResId) ?: return
            setSelectedTabIndicator(tabIndicatorColor)
        }
        if (tabTextColorResId != 0) {
            val tabTextColor = SkinResources.getInstance().getColorStateList(tabTextColorResId) ?: return
            tabTextColors = tabTextColor
        }
    }
}