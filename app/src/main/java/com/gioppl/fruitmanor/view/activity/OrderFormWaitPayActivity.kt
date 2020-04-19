package com.gioppl.fruitmanor.view.activity

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gioppl.fruitmanor.R
import com.gioppl.fruitmanor.bean.OrderFormBean
import com.gioppl.fruitmanor.broadcast.MainBroadcastReceiver
import com.gioppl.fruitmanor.databinding.ActivityOrderFormWaitPayBinding
import com.gioppl.fruitmanor.net.DeleteOrderFormCloud
import com.gioppl.fruitmanor.net.SearchAllOderFormCould
import com.gioppl.fruitmanor.net.UpdateOrderFormModeCloud
import com.gioppl.fruitmanor.tool.RefreshableViewList
import com.gioppl.fruitmanor.view.adapt.OrderAdapt
import kotlinx.android.synthetic.main.activity_order_form_wait_pay.view.*

//取消订单时候，会把订单放在这里，这里是已下单但是没有支付的订单
class OrderFormWaitPayActivity : BaseActivity() {
    private var root: View? = null
    private var rvl: RefreshableViewList? = null
    private var mList = ArrayList<OrderFormBean>()
    private var mRV: RecyclerView? = null
    private var mAdapt: OrderAdapt? = null
    private var allWaitMoney = 0f
    override fun receiveBroadCast(broadCastClassify: MainBroadcastReceiver.BroadCastClassify?, statusCode: Int, msg: Any?) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mBinding = ActivityOrderFormWaitPayBinding.inflate(layoutInflater)
        setContentView(mBinding!!.root)
        root = mBinding.root
        initView()
        initData()
    }

    private fun initView() {
        mRV = findViewById(R.id.rv_order)
        val layoutManager = LinearLayoutManager(this)
        mRV!!.layoutManager = layoutManager
        mRV!!.setHasFixedSize(true)
        mAdapt = OrderAdapt(mList, this, object : OrderAdapt.OrderClickCallBack {
            override fun lookDescription(position: Int) {

            }
        })
        mRV!!.adapter = mAdapt
        mRV!!.itemAnimator = DefaultItemAnimator()
    }

    private fun initData() {
        mList.clear()
        SearchAllOderFormCould(this, 0, SearchAllOderFormCould.NetData { it ->
            mList.addAll(it)
            mAdapt!!.notifyDataSetChanged()
            for (i in 0 until mList.size)
                allWaitMoney += mList[i].serverData.price
            root!!.tv_totalMoney.text=formatPrice(allWaitMoney)
        })

    }

    fun back(view: View) {
        finish()
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

    //模拟支付
    fun payMoney(view: View) {
        mango(this, "支付成功")
        //把订单的模式由未支付换成已支付没发货
        val updateOrderFormModeList = ArrayList<String>();
        for (i in 0 until mList.size) {
            updateOrderFormModeList.add(mList[i].objectId)
        }
        UpdateOrderFormModeCloud(this,1,updateOrderFormModeList)
        mList.clear()
        mAdapt!!.notifyDataSetChanged()
        finish()
    }

    //取消订单
    fun cancelOrder(view: View) {
        mango(this, "订单已取消")
        //删除云端数据
        val deleteObjectIds = ArrayList<String>();
        for (i in 0 until mList.size) {
            deleteObjectIds.add(mList[i].objectId)
        }
        DeleteOrderFormCloud(this, deleteObjectIds)
        mList.clear()
        mAdapt!!.notifyDataSetChanged()
        finish()
    }
}