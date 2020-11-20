package com.ctl.skinaction

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ctl.skinaction.adapter.MyFragmentPagerAdapter
import com.ctl.skinaction.fragment.BuyFragment
import com.ctl.skinaction.fragment.HomeFragment
import com.ctl.skinaction.fragment.PersonalFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        view_pager.adapter = MyFragmentPagerAdapter(
            supportFragmentManager,
            listOf(HomeFragment(), BuyFragment(), PersonalFragment()),
            listOf("首页", "购物", "我的")
        )
        tab_layout.setupWithViewPager(view_pager)
        btn_change.setOnClickListener {
            startActivity(Intent(this, SkinActivity::class.java))
        }
    }
}