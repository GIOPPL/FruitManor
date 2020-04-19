package com.gioppl.fruitmanor.view.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.facebook.drawee.view.SimpleDraweeView
import com.gioppl.fruitmanor.R
import com.gioppl.fruitmanor.broadcast.MainBroadcastReceiver
import com.gioppl.fruitmanor.tool.CocoDialog
import com.gioppl.fruitmanor.tool.SharedPreferencesUtils
import com.gioppl.fruitmanor.view.activity.*

class MyFragment : BaseFragment() {
    private var sim_photo: SimpleDraweeView? = null
    private var tv_nick_name: TextView? = null
    private var tv_money: TextView? = null
    private var tv_couponNum: TextView? = null
    private var ll_coupon: LinearLayout? = null
    private var ll_wait_pay: LinearLayout? = null
    private var ll_already_pay: LinearLayout? = null
    private var ll_wait_receive: LinearLayout? = null
    private var ll_already_receive: LinearLayout? = null

    private var ll_myInfo: LinearLayout? = null
    private var ll_address: LinearLayout? = null
    private var ll_security: LinearLayout? = null
    private var ll_setting: LinearLayout? = null

    private var sim_loginSetting: SimpleDraweeView? = null
    private var tv_loginSetting: TextView? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_my, container, false)
    }

    override fun receiveBroadCast(broadCastClassify: MainBroadcastReceiver.BroadCastClassify, statusCode: Int, msg: Any?) {
        refreshLoginSetting()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
        initData()
    }

    private fun initData() {
        refreshLoginSetting()
    }

    //刷新登陆状态
    private fun refreshLoginSetting() {
        val loginStatus = SharedPreferencesUtils.getInstance().getData("loginStatus", false) as Boolean
        if (loginStatus) {//已经登陆
            tv_nick_name!!.text = SharedPreferencesUtils.getInstance().getData("nickName", "（未命名）") as String
            sim_photo!!.setImageURI(Uri.parse(SharedPreferencesUtils.getInstance().getData("imageUrl", "http://lc-9AEi5aIp.cn-n1.lcfile.com/865bab85a1a7d6f432d6/ic_kiwi.png") as String))
            val money = SharedPreferencesUtils.getInstance().getData("money", 0f) as Float
            tv_money!!.text = formatPrice(money)
            tv_couponNum!!.text = "${SharedPreferencesUtils.getInstance().getData("couponNum", 0) as Int}"
            tv_loginSetting!!.text="退出登陆"
            sim_loginSetting!!.setImageURI(Uri.parse("res://mipmap/${R.mipmap.quit_login}"))
        } else {//没有登陆
            tv_nick_name!!.text = "登陆/注册"
            sim_photo!!.setImageURI(Uri.parse("res://mipmap/${R.mipmap.ic_kiwi}"))
            tv_couponNum!!.text = "0"
            tv_money!!.text = "0"
            tv_loginSetting!!.text="点击登陆"
            sim_loginSetting!!.setImageURI(Uri.parse("res://mipmap/${R.mipmap.quit_login_blue}"))
        }
    }
    //格式化价格，0时显示0，且保留两位小数
    private fun formatPrice(price: Float): String {
        val s = "￥" + String.format("%.2f", price)
        if (s == "￥-0.00" || s == "￥0.00") {
            return "￥0"
        } else {
            return s
        }
    }

    private fun initView() {
        sim_loginSetting = activity!!.findViewById(R.id.sim_loginSetting)
        tv_loginSetting = activity!!.findViewById(R.id.tv_loginSetting)
        ll_myInfo = activity!!.findViewById(R.id.ll_myInfo)
        ll_address = activity!!.findViewById(R.id.ll_address)
        ll_security = activity!!.findViewById(R.id.ll_security)
        ll_setting = activity!!.findViewById(R.id.ll_setting)
        tv_couponNum = activity!!.findViewById(R.id.rv_couponNum)
        ll_coupon = activity!!.findViewById(R.id.ll_coupon)
        ll_wait_pay = activity!!.findViewById(R.id.ll_wait_pay)
        ll_already_pay = activity!!.findViewById(R.id.ll_already_pay)
        ll_wait_receive = activity!!.findViewById(R.id.ll_wait_receive)
        ll_already_receive = activity!!.findViewById(R.id.ll_already_receive)
        tv_money = activity!!.findViewById(R.id.tv_money)
        sim_photo = activity!!.findViewById(R.id.sim_photo)
        tv_nick_name = activity!!.findViewById(R.id.tv_nick_name)
        ll_myInfo!!.setOnClickListener {
            val loginStatus = SharedPreferencesUtils.getInstance().getData("loginStatus", false) as Boolean
            if (loginStatus) {//已经登陆
                val intent=Intent(activity, UpdateUserInfoActivity::class.java)
                intent.putExtra("mode",0)
                startActivity(intent)
            }else{
                BaseActivity.Mango(activity!!, "请先登陆")
                startActivity(Intent(activity, LoginActivity::class.java))
            }
        }
        ll_address!!.setOnClickListener {
            val loginStatus = SharedPreferencesUtils.getInstance().getData("loginStatus", false) as Boolean
            if (loginStatus) {//已经登陆
                val intent=Intent(activity, UpdateUserInfoActivity::class.java)
                intent.putExtra("mode",1)
                startActivity(intent)
            }else{
                BaseActivity.Mango(activity!!, "请先登陆")
                startActivity(Intent(activity, LoginActivity::class.java))
            }
        }
        ll_security!!.setOnClickListener {
            val loginStatus = SharedPreferencesUtils.getInstance().getData("loginStatus", false) as Boolean
            if (loginStatus) {//已经登陆
                val intent=Intent(activity, UpdateUserInfoActivity::class.java)
                intent.putExtra("mode",2)
                startActivity(intent)
            }else{
                BaseActivity.Mango(activity!!, "请先登陆")
                startActivity(Intent(activity, LoginActivity::class.java))
            }
        }
        ll_setting!!.setOnClickListener {
            val loginStatus = SharedPreferencesUtils.getInstance().getData("loginStatus", false) as Boolean
            if (loginStatus){//已经登陆
                val dialog = CocoDialog(activity!!)
                dialog.setMessage("一定要退出登陆嘛？")
                        .setImageResId(R.mipmap.ic_kiwi)
                        .setTitle("果果提示")
                        .setSingle(false)
                        .setOnClickBottomListener(object : CocoDialog.OnClickBottomListener {
                            override fun onPositiveClick() {//退出登陆
                                SharedPreferencesUtils.getInstance().saveData("loginStatus",!loginStatus)
                                refreshLoginSetting()
                                dialog.dismiss()
                            }
                            override fun onNegtiveClick() {//没有退出
                                dialog.dismiss()
                            }
                        }).show()
            }else{//没有登陆
                startActivity(Intent(activity, LoginActivity::class.java))
            }

        }
        tv_nick_name!!.setOnClickListener {
            val loginStatus = SharedPreferencesUtils.getInstance().getData("loginStatus", false) as Boolean
            if (!loginStatus) {
                startActivity(Intent(activity!!, LoginActivity::class.java))
            }else{
                val intent=Intent(activity, UpdateUserInfoActivity::class.java)
                intent.putExtra("mode",0)
                startActivity(intent)
            }
        }
        ll_coupon!!.setOnClickListener {
            val isLogin = SharedPreferencesUtils.getInstance().getData("loginStatus", false) as Boolean
            if (isLogin) {
                startActivity(Intent(activity, CouponActivity::class.java))
            } else {
                BaseActivity.Mango(activity!!, "请先登陆")
                startActivity(Intent(activity, LoginActivity::class.java))
            }
        }
        ll_wait_pay!!.setOnClickListener {
            //待支付的订单
            val isLogin = SharedPreferencesUtils.getInstance().getData("loginStatus", false) as Boolean
            if (isLogin) {
                val intent = Intent(activity, OrderFormWaitPayActivity::class.java)
                startActivity(intent)
            } else {
                BaseActivity.Mango(activity!!, "请先登陆")
                startActivity(Intent(activity, LoginActivity::class.java))
            }
        }
        ll_already_pay!!.setOnClickListener {
            val isLogin = SharedPreferencesUtils.getInstance().getData("loginStatus", false) as Boolean
            if (isLogin) {
                val intent = Intent(activity, OrderFormAllSeeActivity::class.java)
                intent.putExtra("searchMode", 1)//查询已经支付，带配送的订单
                startActivity(intent)
            } else {
                BaseActivity.Mango(activity!!, "请先登陆")
                startActivity(Intent(activity, LoginActivity::class.java))
            }
        }
        ll_wait_receive!!.setOnClickListener {
            val isLogin = SharedPreferencesUtils.getInstance().getData("loginStatus", false) as Boolean
            if (isLogin) {
                val intent = Intent(activity, OrderFormAllSeeActivity::class.java)
                intent.putExtra("searchMode", 2)//查询在配送中的订单
                startActivity(intent)
            } else {
                BaseActivity.Mango(activity!!, "请先登陆")
                startActivity(Intent(activity, LoginActivity::class.java))
            }
        }
        ll_already_receive!!.setOnClickListener {
            val isLogin = SharedPreferencesUtils.getInstance().getData("loginStatus", false) as Boolean
            if (isLogin) {
                val intent = Intent(activity, OrderFormAllSeeActivity::class.java)
                intent.putExtra("searchMode", 3)//查询已经完成交易的订单
                startActivity(intent)
            } else {
                BaseActivity.Mango(activity!!, "请先登陆")
                startActivity(Intent(activity, LoginActivity::class.java))
            }
        }

    }
}
