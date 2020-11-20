package com.ctl.skin_lib

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.text.TextUtils

/**
 * created by : chentl
 * Date: 2020/11/19
 */
class SkinResources(val context: Context) {

    private var isDefaultSkin: Boolean = true
    private var mAppResources: Resources? = null
    private var mSkinResources: Resources? = null
    private var mSkinPackageName: String? = null

    init {
        mAppResources = context.resources
    }

    companion object {
        @Volatile
        private var instance: SkinResources? = null

        fun init(context: Context): SkinResources {
            if (instance == null) {
                synchronized(this) {
                    if (instance == null) {
                        instance = SkinResources(context)
                    }
                }
            }
            return instance!!
        }

        fun getInstance(): SkinResources {
            return instance!!
        }
    }

    fun applySkinResources(resources: Resources?, packageName: String?) {
        mSkinResources = resources
        mSkinPackageName = packageName
        isDefaultSkin = TextUtils.isEmpty(packageName) || resources == null
    }

    fun resetSkinAction() {
        mSkinResources = null
        mSkinPackageName = ""
        isDefaultSkin = true
    }

    fun getIdentifier(resId: Int): Int {
        if (isDefaultSkin) {
            return resId
        }
        if (mAppResources == null) {
            return resId
        }
        val resName: String = mAppResources!!.getResourceEntryName(resId)
        val resType: String = mAppResources!!.getResourceTypeName(resId)
        val skinId = mSkinResources?.getIdentifier(resName, resType, mSkinPackageName) ?: resId
        return skinId
    }

    fun getColor(resId: Int): Int? {
        if (mAppResources == null) {
            return null
        }
        if (isDefaultSkin) {
            return mAppResources!!.getColor(resId)
        }
        val skinId: Int = getIdentifier(resId)
        if (skinId == 0) {
            return mAppResources!!.getColor(resId)
        }
        return mSkinResources?.getColor(skinId) ?: mAppResources!!.getColor(resId)
    }

    @SuppressLint("UseCompatLoadingForColorStateLists")
    fun getColorStateList(resId: Int): ColorStateList? {
        if (mAppResources == null) {
            return null
        }
        if (isDefaultSkin) {
            return mAppResources!!.getColorStateList(resId)
        }
        val skinId: Int = getIdentifier(resId)
        if (skinId == 0) {
            return mAppResources!!.getColorStateList(resId)
        }
        return mSkinResources?.getColorStateList(skinId) ?: mAppResources!!.getColorStateList(resId)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    fun getDrawable(resId: Int): Drawable? {
        if (mAppResources == null) {
            return null
        }
        if (isDefaultSkin) {
            return mAppResources!!.getDrawable(resId)
        }
        val skinId: Int = getIdentifier(resId)
        if (skinId == 0) {
            return mAppResources!!.getDrawable(resId)
        }
        return mSkinResources?.getDrawable(skinId) ?: mAppResources!!.getDrawable(resId)
    }

    fun getBackground(resId: Int): Any? {
        if (mAppResources == null) {
            return null
        }
        val resTypeName = mAppResources!!.getResourceTypeName(resId)
        if ("color" == resTypeName) {
            return getColor(resId)
        } else {
            return getDrawable(resId)
        }
    }
}