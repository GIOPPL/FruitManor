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
import com.gioppl.fruitmanor.tool.SharedPreferencesUtils
import com.gioppl.fruitmanor.view.activity.CouponActivity
import com.gioppl.fruitmanor.view.activity.LoginActivity

class MyFragment : BaseFragment() {
    private var loginStatus=false
    private var sim_photo:SimpleDraweeView?=null
    private var tv_nick_name:TextView?=null
    private var tv_money:TextView?=null
    private var ll_coupon:LinearLayout?=null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_my, container, false)
    }

    override fun receiveBroadCast(broadCastClassify: MainBroadcastReceiver.BroadCastClassify, statusCode: Int, msg: Any?) {
        if (broadCastClassify== MainBroadcastReceiver.BroadCastClassify.LOGIN){
            if (statusCode== MainBroadcastReceiver.STATUS_CODE_0X01){
                tv_nick_name!!.text=SharedPreferencesUtils.getInstance().getData("nickName","（未命名）") as String
                sim_photo!!.setImageURI(Uri.parse(SharedPreferencesUtils.getInstance().getData("imageUrl","http://lc-9AEi5aIp.cn-n1.lcfile.com/865bab85a1a7d6f432d6/ic_kiwi.png")as String))
                val money=SharedPreferencesUtils.getInstance().getData("money",0)as Int
                tv_money!!.text=money.toString()
            }else{
                tv_nick_name!!.text="登陆/注册"
                sim_photo!!.setImageURI(Uri.parse("res://mipmap/${R.mipmap.ic_kiwi}"))
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        loginStatus=SharedPreferencesUtils.getInstance().getData("loginStatus",false) as Boolean
        initView()
        initData()
    }

    private fun initData() {

        if (loginStatus){
            tv_nick_name!!.text=SharedPreferencesUtils.getInstance().getData("nickName","（未命名）") as String
            sim_photo!!.setImageURI(Uri.parse(SharedPreferencesUtils.getInstance().getData("imageUrl","http://lc-9AEi5aIp.cn-n1.lcfile.com/865bab85a1a7d6f432d6/ic_kiwi.png")as String))
        }else{
            tv_nick_name!!.text="登陆/注册"
            sim_photo!!.setImageURI(Uri.parse("res://mipmap/${R.mipmap.ic_kiwi}"))
        }
    }

    private fun initView() {
        ll_coupon=activity!!.findViewById(R.id.ll_coupon)
        tv_money=activity!!.findViewById(R.id.tv_money)
        sim_photo=activity!!.findViewById(R.id.sim_photo)
        tv_nick_name=activity!!.findViewById(R.id.tv_nick_name)
        tv_nick_name!!.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                if (!loginStatus){
                    startActivity(Intent(activity!!,LoginActivity::class.java))
                }
            }
        })
        ll_coupon!!.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                startActivity(Intent(activity,CouponActivity::class.java))
            }
        })
    }
}
