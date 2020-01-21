package com.gioppl.fruitmanor.view.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.facebook.drawee.view.SimpleDraweeView
import com.gioppl.fruitmanor.R
import com.gioppl.fruitmanor.tool.SharedPreferencesUtils
import com.gioppl.fruitmanor.view.activity.LoginActivity

/**
 * Created by GIOPPL on 2017/10/8.
 */
class MyFragment : Fragment() {
    var loginStatus=false
    private var sim_photo:SimpleDraweeView?=null
    private var tv_nick_name:TextView?=null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_my, container, false)
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
        sim_photo=activity!!.findViewById(R.id.sim_photo)
        tv_nick_name=activity!!.findViewById(R.id.tv_nick_name)
        tv_nick_name!!.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                if (!loginStatus){
                    startActivity(Intent(activity!!,LoginActivity::class.java))
                }
            }
        })
    }
}
