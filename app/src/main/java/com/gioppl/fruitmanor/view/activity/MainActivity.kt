package com.gioppl.fruitmanor.view.activity

import android.os.Bundle
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import com.gioppl.fruitmanor.R
import com.gioppl.fruitmanor.broadcast.MainBroadcastReceiver
import com.gioppl.fruitmanor.tool.BanSlidingViewPager
import com.gioppl.fruitmanor.tool.PermissionUtils
import com.gioppl.fruitmanor.tool.SharedPreferencesUtils
import com.gioppl.fruitmanor.view.fragment.ClassifyFragment
import com.gioppl.fruitmanor.view.fragment.HomeFragment
import com.gioppl.fruitmanor.view.fragment.MyFragment
import com.gioppl.fruitmanor.view.fragment.ShopCarFragment
import com.gioppl.fruitmanor.view.view.RedPointView
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class MainActivity : BaseActivity() {
    var vp: BanSlidingViewPager? = null
    var mRadioGroup: RadioGroup? = null
    var mPagerList = ArrayList<Fragment>()
    var redPointView:RedPointView?=null

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

    override fun onStop() {
        super.onStop()
        if (EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this)
    }

    override fun onStart() {
        super.onStart()
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this)
    }
    //如果在onCreate中运行则获取到的值为0，因为控件没有初始化好
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        val rbtn_shoppingCart=findViewById<RadioButton>(R.id.rbtn_main_three)
        val shoppingCartWidth=rbtn_shoppingCart.width
        val shoppingCartHeight=rbtn_shoppingCart.height
        val rbtn_location = IntArray(2)
        rbtn_shoppingCart.getLocationInWindow(rbtn_location)
        redPointView=RedPointView(this,rbtn_location,shoppingCartWidth,shoppingCartHeight)
        val rl_home=findViewById<RelativeLayout>(R.id.rl_home)
        rl_home.addView(redPointView)
        val isLogin = SharedPreferencesUtils.getInstance().getData("loginStatus", false) as Boolean
        if (isLogin) {//已经登陆了
            val shopHaveGoods=SharedPreferencesUtils.getInstance().getData("redPointNum", 0) as Int
            redPointView!!.redPointParams(shopHaveGoods)
        }else{
            redPointView!!.redPointParams(0)
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


    //EventBus分发消息的类,isAdd总共有两种模式，true是增加模式，false是设置模式,默认为增加模式
    class MessageEvent(val num:Int,val isAdd:Boolean=true)
    class RedPointMessageEvent(val isRefresh:Boolean=true)
    @Subscribe
    fun onMessageEvent(msg: RedPointMessageEvent){
        if (msg.isRefresh)
            redPointView!!.refreshRedPoint()
    }
}