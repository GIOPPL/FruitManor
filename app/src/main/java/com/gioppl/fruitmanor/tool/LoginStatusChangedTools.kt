package com.gioppl.fruitmanor.tool

import android.content.Context
import com.gioppl.fruitmanor.net.SearchCouponCould
import com.gioppl.fruitmanor.net.SearchShopCartCould

class LoginStatusChangedTools(private var activity: Context)  {
    init {
        val isLogin = SharedPreferencesUtils.getInstance().getData("loginStatus", false) as Boolean
        if (isLogin) {//已经登陆了
            SearchShopCartCould(activity)//获取购物车信息
            SearchCouponCould(activity)//获取优惠券信息

        }
    }

}