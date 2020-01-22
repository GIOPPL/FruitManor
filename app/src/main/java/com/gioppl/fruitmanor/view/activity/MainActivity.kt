package com.gioppl.fruitmanor.view.activity

import android.os.Bundle
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import com.gioppl.fruitmanor.R
import com.gioppl.fruitmanor.broadcast.MainBroadcastReceiver
import com.gioppl.fruitmanor.tool.BanSlidingViewPager
import com.gioppl.fruitmanor.tool.PermissionUtils
import com.gioppl.fruitmanor.view.fragment.ClassifyFragment
import com.gioppl.fruitmanor.view.fragment.HomeFragment
import com.gioppl.fruitmanor.view.fragment.MyFragment
import com.gioppl.fruitmanor.view.fragment.ShopCarFragment

class MainActivity : BaseActivity() {
    var vp: BanSlidingViewPager? = null
    var mRadioGroup: RadioGroup? = null
    var mPagerList = ArrayList<Fragment>()

    override fun receiveBroadCast(broadCastClassify: MainBroadcastReceiver.BroadCastClassify?, statusCode: Int, msg: Any?) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        initPager()
        PermissionUtils.isGrantExternalRW(this, 1)
    }
    private fun initView() {
        vp = findViewById(R.id.vp_main) as BanSlidingViewPager?
        mRadioGroup = findViewById(R.id.rg_main_bottom) as RadioGroup?
        mRadioGroup!!.check(R.id.rbtn_main_one)
        mRadioGroup!!.setOnCheckedChangeListener {
            radioGroup, i ->
            when (i) {
                R.id.rbtn_main_one -> {
                    vp!!.currentItem = 0
                }
                R.id.rbtn_main_two -> {
                    vp!!.currentItem = 1
                }
                R.id.rbtn_main_three -> {
                    vp!!.currentItem = 2
                }
                R.id.rbtn_main_four -> {
                    vp!!.currentItem = 3
                }
            }
        }
    }
    private fun initPager() {
        mPagerList.add(HomeFragment())
        mPagerList.add(ClassifyFragment())
        mPagerList.add(ShopCarFragment())
        mPagerList.add(MyFragment())
        val pagerAdapt = object : FragmentPagerAdapter(supportFragmentManager) {
            override fun getItem(position: Int): Fragment = mPagerList.get(position)
            override fun getCount(): Int = mPagerList.size
        }
        vp!!.adapter = pagerAdapt
    }

//    public interface BroadCastCallback{
//        fun onChangeListener(broadCastClassify: BroadCastClassify, statusCode: Int, msg: Any?)
//    }
}