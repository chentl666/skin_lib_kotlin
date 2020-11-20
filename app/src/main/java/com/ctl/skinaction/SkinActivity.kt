package com.ctl.skinaction

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import com.ctl.skin_lib.SkinAction
import kotlinx.android.synthetic.main.activity_skin.*
import java.io.File

class SkinActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_skin)
        btn_change.setOnClickListener {
            val skin = this.getExternalFilesDir(null)
            if (skin != null) {
                SkinAction.getInstance()
                    .loadSkinPackage(skin.absolutePath + File.separator + "skin_package-debug.apk")
            }
        }
        btn_reset.setOnClickListener {
            SkinAction.getInstance().loadSkinPackage(null)
        }
    }
}