package com.gioppl.fruitmanor.view.activity

import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gioppl.fruitmanor.MyApplication
import com.gioppl.fruitmanor.R
import com.gioppl.fruitmanor.bean.CouponBean
import com.gioppl.fruitmanor.bean.OrderFormBean
import com.gioppl.fruitmanor.broadcast.MainBroadcastReceiver
import com.gioppl.fruitmanor.databinding.ActivityOrderFormCreateBinding
import com.gioppl.fruitmanor.net.AddOderFormCould
import com.gioppl.fruitmanor.net.DeleteGoodsFromShopCartCloud
import com.gioppl.fruitmanor.net.UpdateCouponStatusCloud
import com.gioppl.fruitmanor.net.UpdateUserMoneyCloud
import com.gioppl.fruitmanor.sql.Table
import com.gioppl.fruitmanor.tool.RefreshableViewList
import com.gioppl.fruitmanor.tool.SharedPreferencesUtils
import com.gioppl.fruitmanor.view.adapt.OrderAdapt
import kotlinx.android.synthetic.main.activity_order_form_create.view.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

//创建订单的界面
class OrderFormCreateActivity : BaseActivity() {
    private var root: View? = null
    private var rvl: RefreshableViewList? = null
    private var mList = ArrayList<OrderFormBean>()
    private var mRV: RecyclerView? = null
    private var mAdapt: OrderAdapt? = null
    private var couponsList = ArrayList<CouponBean>()
    private var selectCouponsList = ArrayList<CouponBean>()
    private var moneyDiscount = 0f
    private var goodsTotalPrice = 0f
    override fun receiveBroadCast(broadCastClassify: MainBroadcastReceiver.BroadCastClassify?, statusCode: Int, msg: Any?) {
        val address = SharedPreferencesUtils.getInstance().getData("address", "") as String
        if (address != "") {
            root!!.tv_address.text = address
        } else {
            root!!.tv_address.text = "请填写收货地址"
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mBinding = ActivityOrderFormCreateBinding.inflate(layoutInflater)
        setContentView(mBinding!!.root)
        root = mBinding.root
        getGoodsFromDb()
        initView()
    }

    //查询数据库中的所有优惠券
    private fun getGoodsFromDb() {
        couponsList.clear()
        val sql = "select * from ${Table.CouponTable.TABLE_NAME}"
        val mCursor: Cursor = MyApplication.getInstance().dbHelper.exeSql(sql)
        while (mCursor.moveToNext()) {
            val bean = CouponBean()
            bean.objectId=mCursor.getString(mCursor.getColumnIndex(Table.CouponTable.OBJECT_ID))
            bean.goods_id = mCursor.getString(mCursor.getColumnIndex(Table.CouponTable.GOODS_ID))
            bean.reduce_money = mCursor.getInt(mCursor.getColumnIndex(Table.CouponTable.REDUCE_MONEY))
            bean.imageUrl = mCursor.getString(mCursor.getColumnIndex(Table.CouponTable.IMAGE_URL))
            bean.title = mCursor.getString(mCursor.getColumnIndex(Table.CouponTable.TITLE))
            bean.endTime = mCursor.getString(mCursor.getColumnIndex(Table.CouponTable.END_TIME))
            couponsList.add(bean)
        }

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

        val address = SharedPreferencesUtils.getInstance().getData("address", "") as String
        if (address != "") {
            root!!.tv_address.text = address
        } else {
            root!!.tv_address.text = "请填写收货地址"
        }
        root!!.ll_addressChoice.setOnClickListener {
            val intent = Intent(this, UpdateUserInfoActivity::class.java)
            intent.putExtra("mode", 1)
            startActivity(intent)
        }
    }

    fun back(view: View) {
        finish()
    }

    //立即购买，模拟支付//mode模式，0未支付1为没配送2正在配送3完成
    fun payMoney(view: View) {
        mango(this, "支付成功")
        val orderShopIdList = ArrayList<String>()
        //添加订单
        for (i in 0 until mList.size) {
            val bean = mList[i].serverData
            orderShopIdList.add(bean.shopId)
            AddOderFormCould(this, bean.fruitId, bean.imageUrl, 1, bean.price, bean.title)
        }
        //删除删除数据库和云端购物车中的商品
        deleteShopCart()
        //删除数据库中的优惠券和云端的优惠券
        deleteCoupon()
        //减少用户余额
        reduceUserMoney()
        finish()
    }
    //减少用户余额
    private fun reduceUserMoney() {
        val userObjectId=SharedPreferencesUtils.getInstance().getData("objectId","") as String
        val userMoney=SharedPreferencesUtils.getInstance().getData("money",0f) as Float
        UpdateUserMoneyCloud(this,userObjectId,userMoney-goodsTotalPrice+moneyDiscount)
        SharedPreferencesUtils.getInstance().saveData("money",userMoney-goodsTotalPrice+moneyDiscount)
    }

    //删除删除数据库和云端购物车中的商品
    private fun deleteShopCart() {
        val db = MyApplication.getInstance().dbHelper.writableDatabase
        val shopCartGoods=ArrayList<String>()
        for (i in 0 until mList.size){
            shopCartGoods.add(mList[i].serverData.shopId)
            //删除本地数据库
//            val sql = "delete from " + Table.ShopCartTable.TABLE_NAME + " where shop_id='" + mList[i].serverData.shopId + "'"
//            db.execSQL(sql)
        }
        //删除云端
        DeleteGoodsFromShopCartCloud(this,shopCartGoods)
    }
    //删除数据库中的优惠券和云端的优惠券
    private fun deleteCoupon() {
        val db = MyApplication.getInstance().dbHelper.writableDatabase
        db.beginTransaction()

        for (i in 0 until selectCouponsList.size) {
            val sql = "delete from " + Table.CouponTable.TABLE_NAME + " where object_id='" + selectCouponsList[i].objectId + "'"
            db.execSQL(sql)
            UpdateCouponStatusCloud(this, selectCouponsList[i].objectId, 1)
        }
        db.endTransaction()
    }

    //稍后购买
    fun waitPayMoney(view: View) {
        mango(this, "您可以在订单中继续支付该订单")
        val orderShopIdList = ArrayList<String>()
        for (i in 0 until mList.size) {
            val bean = mList[i].serverData
            orderShopIdList.add(bean.shopId)
            AddOderFormCould(this, bean.fruitId, bean.imageUrl, 0, bean.price, bean.title)
        }
        EventBus.getDefault().post(orderShopIdList)
        finish()
    }



    //用于接收订单
    @Subscribe(sticky = true)
    fun onMessageEvent(ordersList: ArrayList<OrderFormBean.ServerDataBean>) {
        mList.clear()
        goodsTotalPrice = 0f
        moneyDiscount = 0f
        for (i in 0 until ordersList.size) {
            val bean = OrderFormBean()
            bean.objectId=ordersList[i].shopId
            if((ordersList as ArrayList<OrderFormBean.ServerDataBean>)[i].fruitId!=null){
                bean.serverData = (ordersList as ArrayList<OrderFormBean.ServerDataBean>)[i]
                mList.add(bean)
            }
        }
        for (i in 0 until mList.size) {
            goodsTotalPrice += mList[i].serverData.price
            for (j in 0 until couponsList.size) {
                if (mList[i].serverData.fruitId == couponsList[j].goods_id) {
                    moneyDiscount += couponsList[j].reduce_money
                    selectCouponsList.add(couponsList[j])
                }
            }
        }
        root!!.tv_subtotal.text = formatPrice(goodsTotalPrice)
        root!!.tv_totalMoney.text = formatPrice(goodsTotalPrice - moneyDiscount)
        root!!.tv_discount.text = formatPrice(moneyDiscount)
        mAdapt!!.notifyDataSetChanged()
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