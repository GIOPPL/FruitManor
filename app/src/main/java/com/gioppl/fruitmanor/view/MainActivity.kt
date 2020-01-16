package com.gioppl.fruitmanor.view

import android.os.Bundle
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.gioppl.fruitmanor.R
import com.gioppl.fruitmanor.view.fragment.ClassifyFragment
import com.gioppl.fruitmanor.view.fragment.HomeFragment
import com.gioppl.fruitmanor.view.fragment.ShopCarFragment

class MainActivity : BaseActivity() {
    var vp: ViewPager? = null
    var mRadioGroup: RadioGroup? = null
    var mPagerList = ArrayList<Fragment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        initPager()
    }
    private fun initView() {
        vp = findViewById(R.id.vp_main) as ViewPager?
        mRadioGroup = findViewById(R.id.rg_main_bottom) as RadioGroup?
        mRadioGroup!!.check(R.id.rbtn_main_one)
        mRadioGroup!!.setOnCheckedChangeListener {
            radioGroup, i ->
            when (i) {
                R.id.rbtn_main_one -> {
                    vp!!.setCurrentItem(0)
                }
                R.id.rbtn_main_two -> {
                    vp!!.setCurrentItem(1)
                }
                R.id.rbtn_main_three -> {
                    vp!!.setCurrentItem(2)
                }
            }
        }
    }
    private fun initPager() {
        mPagerList.add(HomeFragment())
        mPagerList.add(ClassifyFragment())
        mPagerList.add(ShopCarFragment())
        var pagerAdapt = object : FragmentPagerAdapter(supportFragmentManager) {
            override fun getItem(position: Int): Fragment = mPagerList.get(position)
            override fun getCount(): Int = mPagerList.size
        }
        vp!!.adapter = pagerAdapt
    }
}