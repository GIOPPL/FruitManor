package com.gioppl.fruitmanor.view.activity

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.View
import com.gioppl.fruitmanor.bean.NetFruitBean
import com.gioppl.fruitmanor.bean.OrderFormBean
import com.gioppl.fruitmanor.broadcast.MainBroadcastReceiver
import com.gioppl.fruitmanor.databinding.ActivityGoodsLookBinding
import com.gioppl.fruitmanor.net.AddGoodsToShopCartCloud
import com.gioppl.fruitmanor.tool.SharedPreferencesUtils
import kotlinx.android.synthetic.main.activity_goods_look.view.*
import kotlinx.android.synthetic.main.include_goods_info.view.*
import kotlinx.android.synthetic.main.include_goods_scroll.view.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

//商品的详细界面
class GoodsLookActivity : BaseActivity() {
    private var root: View? = null
    private var objectId = ""
    private var fruitBean: NetFruitBean?=null
    override fun receiveBroadCast(broadCastClassify: MainBroadcastReceiver.BroadCastClassify?, statusCode: Int, msg: Any?) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mBinding = ActivityGoodsLookBinding.inflate(layoutInflater)
        setContentView(mBinding!!.root)
        root = mBinding.root
        initTop()
        initView()
    }

    @Subscribe(sticky = true)
    fun onMessageEvent2(fruitBean: NetFruitBean) {
        this.fruitBean=fruitBean
        objectId = fruitBean.objectId
        val data = fruitBean.serverData
        root!!.tv_title.text = data.title
        root!!.tv_subtitle.text = data.subtitle
        root!!.tv_price.text = data.price.toString()
        root!!.tv_total_sale.text = data.totalSale.toString()
        root!!.tv_goodsSendPattern.text = data.goodsSendPattern
        root!!.tv_producingArea.text = data.producingArea
        root!!.tv_scalage.text = data.scalage
        root!!.tv_goodsWeight.text = data.goodsWeight
        root!!.tv_goodsProducingDate.text = data.goodsProducingDate
        root!!.tv_goodsFreshDate.text = data.goodsFreshDate
        root!!.sim_mainImage.setImageURI(Uri.parse(data.imageUrl))
        root!!.sim_goodsImage1.setImageURI(Uri.parse(data.goodsImage1))
        root!!.sim_goodsImage2.setImageURI(Uri.parse(data.goodsImage2))
        root!!.sim_goodsImage3.setImageURI(Uri.parse(data.goodsImage3))
    }

    private fun initView() {
        root!!.ll_coupon_get.setOnClickListener {
            val intent=Intent(this, GoodsCouponGetActivity::class.java)
            intent.putExtra("fruitId",objectId)
            startActivity(intent)
        }

    }

    private fun initTop() {
        root!!.sv_goods.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (scrollY < 255 && scrollY != 0) {
                root!!.tv_top_goods.visibility = View.VISIBLE
                root!!.tv_top_details.visibility = View.VISIBLE
                val hex = Integer.toHexString(scrollY)
                var s = "";
                if (scrollY <= 15) {
                    s = "0${hex}";
                } else {
                    s = hex;
                }
                if (s.length == 2) {
                    root!!.rl_top.setBackgroundColor(Color.parseColor("#${s}FFFFFF"))
                    root!!.tv_top_goods.setTextColor(Color.parseColor("#${s}FB3379"))
                    root!!.tv_top_details.setTextColor(Color.parseColor("#${s}aaaaaa"))
                }

            } else if (scrollY == 0) {
                root!!.tv_top_goods.visibility = View.GONE
                root!!.tv_top_details.visibility = View.GONE
                root!!.rl_top.setBackgroundColor(Color.parseColor("#00ffffff"))
            } else {
                root!!.tv_top_goods.visibility = View.VISIBLE
                root!!.tv_top_details.visibility = View.VISIBLE
                root!!.rl_top.setBackgroundColor(Color.parseColor("#FFFFFFFF"))
            }
        }
    }

    fun back(view: View) {
        finish()
    }

    fun openSHopCart(view: View) {
        startActivity(Intent(this, ShopCartActivity::class.java))
    }

    //添加到购物车
    fun addToShopCart(view: View) {
        val isLogin = SharedPreferencesUtils.getInstance().getData("loginStatus", false) as Boolean
        if (!isLogin) {//查看是否登陆
            startActivity(Intent(this, LoginActivity::class.java))
            mango(this, "请先登陆")
            return
        } else {
            //红点加一
            val redPointNum =  SharedPreferencesUtils.getInstance().getData("redPointNum",0) as Int
            SharedPreferencesUtils.getInstance().saveData("redPointNum", redPointNum+1)
            EventBus.getDefault().post(MainActivity.RedPointMessageEvent())

            val userPhone = SharedPreferencesUtils.getInstance().getData("phoneNumber", "") as String
            AddGoodsToShopCartCloud(this, userPhone, objectId)
            mango(this, "已添加")
        }
    }

    //现在就买
    fun buyNow(view: View) {
        val serverData=fruitBean!!.serverData
        val ordersList= ArrayList<OrderFormBean.ServerDataBean>();
        val bean=OrderFormBean.ServerDataBean(serverData.price,objectId,serverData.title,serverData.imageUrl,0,"")
        ordersList.add(bean)
        EventBus.getDefault().postSticky(ordersList)
        startActivity(Intent(this, OrderFormCreateActivity::class.java))
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
}