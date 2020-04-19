package com.gioppl.fruitmanor.tool

import android.content.Context
import android.content.Intent
import com.gioppl.fruitmanor.broadcast.MainBroadcastReceiver
import com.gioppl.fruitmanor.net.SearchCouponCould
import com.gioppl.fruitmanor.net.SearchShopCartCould
import com.gioppl.fruitmanor.net.UserLoginCould

class LoginStatusChangedTools(private var activity: Context) :SearchShopCartCould.ShopCartInfoBack ,UserLoginCould.UserInfoBack{
    private var sumBack=0;
    init {
        val isLogin = SharedPreferencesUtils.getInstance().getData("loginStatus", false) as Boolean
        if (isLogin) {//已经登陆了
            val phoneNum=SharedPreferencesUtils.getInstance().getData("phoneNumber","")as String
            val password=SharedPreferencesUtils.getInstance().getData("password","")as String
//            SearchShopCartCould(activity,this)//获取购物车信息
            SearchCouponCould(activity)//获取优惠券信息
            UserLoginCould(activity,phoneNum,password,this)//获取用户信息
        }
    }

    override fun status(isDownLoad: Boolean) {
        sumBack++
        sendBroadcast()
    }
    override fun statusUserInfo(isDownLoad: Boolean) {
        sumBack++
        sendBroadcast()
    }

    private fun sendBroadcast(){
        if (sumBack==1){
            val intent= Intent();
            intent.action= MainBroadcastReceiver.BROADCAST_ACTION_LOGIN_STATUS
            intent.putExtra("LOGIN_STATUS",true)
            Thread(Runnable {
                try {
                    Thread.sleep(500)
                    activity.sendBroadcast(intent)
                } catch (ex: InterruptedException) {
                    ex.printStackTrace()
                }
            }).run()
            sumBack=0;
        }
    }



}