package com.ctl.skin_lib

import android.app.Application
import android.content.pm.PackageManager
import android.content.res.AssetManager
import android.content.res.Resources
import android.text.TextUtils
import java.io.File
import java.util.*

/**
 * created by : chentl
 * Date: 2020/11/19
 */
class SkinAction(private val mContext: Application) : Observable() {

    init {
        SkinPreference.init(mContext)
        SkinResources.init(mContext)
        mContext.registerActivityLifecycleCallbacks(ApplicationActivityLifecycle(this))
        loadSkinPackage(SkinPreference.getInstance().getSkin())
    }

    companion object {
        @Volatile
        private var instance: SkinAction? = null

        fun initAction(application: Application): SkinAction {
            if (instance == null) {
                synchronized(this) {
                    if (instance == null) {
                        instance = SkinAction(application)
                    }
                }
            }
            return instance!!
        }

        fun getInstance(): SkinAction {
            return instance!!
        }
    }

    fun loadSkinPackage(skinPath: String?) {

        if (TextUtils.isEmpty(skinPath) || skinPath == null || !File(skinPath).exists()) {
            SkinPreference.getInstance().reset()
            SkinResources.getInstance().resetSkinAction()
        } else {
            try {
                val appResources = mContext.resources

                val assetManager = AssetManager::class.java.newInstance()
                val addAssetPath =
                    assetManager.javaClass.getMethod("addAssetPath", String::class.java)
                addAssetPath.isAccessible = true
                addAssetPath.invoke(assetManager, skinPath)

                val skinResources =
                    Resources(assetManager, appResources.displayMetrics, appResources.configuration)

                val packageManager = mContext.packageManager
                val packageArchiveInfo =
                    packageManager.getPackageArchiveInfo(skinPath, PackageManager.GET_ACTIVITIES)
                val packageName = packageArchiveInfo?.packageName

                SkinResources.getInstance().applySkinResources(skinResources, packageName)
                SkinPreference.getInstance().setSkin(skinPath)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        setChanged()
        notifyObservers()
    }

}