package com.gioppl.fruitmanor.view.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import com.gioppl.fruitmanor.broadcast.MainBroadcastReceiver
import com.gioppl.fruitmanor.databinding.ActivityGoodsLookBinding
import kotlinx.android.synthetic.main.activity_goods_look.view.*


class GoodsLookActivity : BaseActivity() {
    private var root:View?=null
    override fun receiveBroadCast(broadCastClassify: MainBroadcastReceiver.BroadCastClassify?, statusCode: Int, msg: Any?) {

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mBinding=ActivityGoodsLookBinding.inflate(layoutInflater)
        setContentView(mBinding!!.root)
        root=mBinding.root
        initTop()

    }

    private fun initTop() {
        root!!.sv_goods.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (scrollY<255&&scrollY!=0){
                root!!.tv_top_goods.visibility=View.VISIBLE
                root!!.tv_top_details.visibility=View.VISIBLE
                val hex = Integer.toHexString(scrollY)
                var s="";
                if(scrollY<=15){
                    s="0${hex}";
                }else{
                    s=hex;
                }
                if (s.length==2){
                    root!!.rl_top.setBackgroundColor(Color.parseColor("#${s}FFFFFF"))
                    root!!.tv_top_goods.setTextColor(Color.parseColor("#${s}FB3379"))
                    root!!.tv_top_details.setTextColor(Color.parseColor("#${s}aaaaaa"))
                }

            }else if(scrollY==0){
                root!!.tv_top_goods.visibility=View.GONE
                root!!.tv_top_details.visibility=View.GONE
                root!!.rl_top.setBackgroundColor(Color.parseColor("#00ffffff"))
            }else{
                root!!.tv_top_goods.visibility=View.VISIBLE
                root!!.tv_top_details.visibility=View.VISIBLE
                root!!.rl_top.setBackgroundColor(Color.parseColor("#FFFFFFFF"))
            }
        }
    }

    fun back(view: View) {
        finish()
    }

    fun openSHopCart(view: View) {
        startActivity(Intent(this,ShopCartActivity::class.java))
    }
    fun addToShopCart(view: View) {}
    fun buyNow(view: View) {}
}