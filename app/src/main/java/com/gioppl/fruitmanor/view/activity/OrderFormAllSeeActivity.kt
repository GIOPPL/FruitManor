package com.gioppl.fruitmanor.view.activity

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gioppl.fruitmanor.R
import com.gioppl.fruitmanor.bean.OrderFormBean
import com.gioppl.fruitmanor.broadcast.MainBroadcastReceiver
import com.gioppl.fruitmanor.databinding.ActivityOrderFormsAllSeeBinding
import com.gioppl.fruitmanor.net.SearchAllOderFormCould
import com.gioppl.fruitmanor.tool.RefreshableViewList
import com.gioppl.fruitmanor.view.adapt.OrderAdapt
import kotlinx.android.synthetic.main.activity_order_form_create.view.*

//查看订单的界面，包括待配送，配送中，已完成的订单
class OrderFormAllSeeActivity : BaseActivity() {
    private var root: View? = null
    private var rvl: RefreshableViewList? = null
    private var mList = ArrayList<OrderFormBean>()
    private var mRV: RecyclerView? = null
    private var mAdapt: OrderAdapt? = null
    override fun receiveBroadCast(broadCastClassify: MainBroadcastReceiver.BroadCastClassify?, statusCode: Int, msg: Any?) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mBinding = ActivityOrderFormsAllSeeBinding.inflate(layoutInflater)
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
        val mode=this.intent.getIntExtra("searchMode",1)
        when(mode){
            1->{root!!.tv_activityAim.text="待配送订单"}
            2->{root!!.tv_activityAim.text="正在配送中订单"}
            3->{root!!.tv_activityAim.text="交易完成订单"}
        }
        mList.clear()
        SearchAllOderFormCould(this,mode, SearchAllOderFormCould.NetData { it ->
            mList.addAll(it)
            mAdapt!!.notifyDataSetChanged()
        })
    }

    fun back(view: View) {
        finish()
    }
}