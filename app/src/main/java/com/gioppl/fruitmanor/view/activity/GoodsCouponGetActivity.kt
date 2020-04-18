package com.gioppl.fruitmanor.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.gioppl.fruitmanor.bean.CouponBean
import com.gioppl.fruitmanor.broadcast.MainBroadcastReceiver
import com.gioppl.fruitmanor.databinding.ActivityGoodsCouponGetBinding
import com.gioppl.fruitmanor.tool.RefreshableViewList
import com.gioppl.fruitmanor.tool.SharedPreferencesUtils
import com.gioppl.fruitmanor.view.adapt.GoodsCouponGetAdapt
import kotlinx.android.synthetic.main.activity_goods_coupon_get.view.*

class GoodsCouponGetActivity : BaseActivity() {
    private var root: View?=null
    private var mList = ArrayList<CouponBean>()
    private var mAdapt: GoodsCouponGetAdapt? = null
    override fun receiveBroadCast(broadCastClassify: MainBroadcastReceiver.BroadCastClassify, statusCode: Int, msg: Any?) {
        if (broadCastClassify == MainBroadcastReceiver.BroadCastClassify.LOGIN) {
            if (statusCode == MainBroadcastReceiver.STATUS_CODE_0X01) {//已经登陆
                root!!.ll_login.visibility = View.GONE
            } else {
                root!!.ll_login.visibility = View.VISIBLE
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mBinding= ActivityGoodsCouponGetBinding.inflate(layoutInflater)
        setContentView(mBinding!!.root)
        root=mBinding.root
        setAdaptManager()
        initView()
        initData()
    }

    private fun initView() {
        root!!.rl_coupon.setOnRefreshListener(1, object : RefreshableViewList.RefreshCallBack {
            override fun onRefresh() {
                Thread(Runnable {
                    Thread.sleep(1000)
                    root!!.rl_coupon.finishRefresh()
                }).start()
            }
            override fun onFinished() {
            }

        })
        root!!.ll_login.setOnClickListener {
            startActivity(Intent(this@GoodsCouponGetActivity, LoginActivity::class.java))
        }



    }

    private fun setAdaptManager() {
        val layoutManager = LinearLayoutManager(this)
        root!!.rv_coupon!!.layoutManager = layoutManager
        root!!.rv_coupon!!.setHasFixedSize(true)
        mAdapt = GoodsCouponGetAdapt(mList, this, object : GoodsCouponGetAdapt.CouponClickCallBack {
            override fun getCouponMsg(position: Int) {
                mango(this@GoodsCouponGetActivity,"点击了：$position")
            }
        })
        root!!.rv_coupon!!.adapter = mAdapt
        root!!.rv_coupon!!.setItemAnimator(DefaultItemAnimator())
    }
    private fun initData() {
        val isLogin = SharedPreferencesUtils.getInstance().getData("loginStatus", false) as Boolean
        if (isLogin) {
            root!!.ll_login.visibility = View.GONE
        } else {
            root!!.ll_login.visibility = View.VISIBLE
        }
        addData()
    }
    fun back(view: View) {
        finish()
    }
    private fun addData(){
        for (i in 1..10){
            val bean=CouponBean(10,"5e245cdd43c257006f39a829","2020-3-17","GIOPPL","http://lc-d2693j76.cn-n1.lcfile.com/52fb246cdb7d2b90006b/cheer.jpg","主题")
            mList.add(bean)
        }
        mAdapt!!.notifyDataSetChanged()
    }
}