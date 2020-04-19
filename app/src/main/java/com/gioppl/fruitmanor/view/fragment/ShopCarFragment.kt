package com.gioppl.fruitmanor.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.gioppl.fruitmanor.MyApplication
import com.gioppl.fruitmanor.R
import com.gioppl.fruitmanor.bean.HomeFruitBean
import com.gioppl.fruitmanor.bean.OrderFormBean
import com.gioppl.fruitmanor.broadcast.MainBroadcastReceiver
import com.gioppl.fruitmanor.databinding.FragmentShopCarBinding
import com.gioppl.fruitmanor.net.DeleteGoodsFromShopCartCloud
import com.gioppl.fruitmanor.net.SearchShopCartCould
import com.gioppl.fruitmanor.sql.MyDbHelper
import com.gioppl.fruitmanor.sql.Table
import com.gioppl.fruitmanor.tool.CocoDialog
import com.gioppl.fruitmanor.tool.RefreshableViewList
import com.gioppl.fruitmanor.tool.SharedPreferencesUtils
import com.gioppl.fruitmanor.view.activity.LoginActivity
import com.gioppl.fruitmanor.view.activity.MainActivity
import com.gioppl.fruitmanor.view.activity.OrderFormCreateActivity
import com.gioppl.fruitmanor.view.adapt.ShopCarAdapt
import kotlinx.android.synthetic.main.activity_shop_cart.view.*
import kotlinx.android.synthetic.main.fragment_shop_car.view.im_all_select
import kotlinx.android.synthetic.main.fragment_shop_car.view.ll_all_select
import kotlinx.android.synthetic.main.fragment_shop_car.view.ll_login
import kotlinx.android.synthetic.main.fragment_shop_car.view.rv_shop_car
import kotlinx.android.synthetic.main.fragment_shop_car.view.rvl_shop
import kotlinx.android.synthetic.main.fragment_shop_car.view.tv_all_price
import kotlinx.android.synthetic.main.fragment_shop_car.view.tv_pay
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.text.DecimalFormat


class ShopCarFragment : BaseFragment() {
    private var totalPrice = 0.0f;
    private var root: View? = null
    private var mList = ArrayList<HomeFruitBean>()
    private var mAdapt: ShopCarAdapt? = null
    private var mDbHelper: MyDbHelper? = null
    private var mSelectList = ArrayList<HomeFruitBean>()
    override fun receiveBroadCast(broadCastClassify: MainBroadcastReceiver.BroadCastClassify, statusCode: Int, msg: Any?) {
        val statusCode=SharedPreferencesUtils.getInstance().getData("loginStatus",false)as Boolean
        if (statusCode) {//已经登陆
            root!!.ll_login.visibility = View.GONE
            getDataFromCould()
            EventBus.getDefault().post(MainActivity.RedPointMessageEvent())
        } else {
            root!!.ll_login.visibility = View.VISIBLE
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val mBinding = FragmentShopCarBinding.inflate(inflater)
        root = mBinding.root
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mDbHelper = MyApplication.getInstance().dbHelper
        setAdaptManager()
        initView()
        initData()
    }

    //更新总价格文本，add为加多少钱，如果是减则为负数,如果总价格大于0 支付按钮的颜色就会变蓝色
    private fun updateTotalPrice(add: Float, onlyUpdate: Boolean = false) {
        if (!onlyUpdate) {
            totalPrice += add
        } else {
            totalPrice = 0f
            for (i in 0 until mSelectList.size)
                totalPrice += mSelectList[i].price
        }
        val decimalFormat = DecimalFormat(".00") //构造方法的字符格式这里如果小数不足2位,会以0补足.
        val s = "￥" + String.format("%.2f", totalPrice)
        if (s == "￥-0.00" || s == "￥0.00") {
            root!!.tv_all_price.text = "￥0"
        } else {
            root!!.tv_all_price.text = s
        }
        if (totalPrice > 0) {
            root!!.tv_pay.background = activity!!.getDrawable(R.drawable.rect_second_color_10dp)
            root!!.tv_pay.setTextColor(ContextCompat.getColor(activity!!, R.color.white))
        } else {
            root!!.tv_pay.background = activity!!.getDrawable(R.drawable.rect_grey_10dp)
            root!!.tv_pay.setTextColor(ContextCompat.getColor(activity!!, R.color.noFocusColor))
        }
    }

    private fun updateOtherButton() {
        if (mSelectList.size == mList.size) {
            root!!.im_all_select.setImageDrawable(activity!!.getDrawable(R.mipmap.select_pressed))
            root!!.im_all_select.tag = "select"
        } else {
            root!!.im_all_select.setImageDrawable(activity!!.getDrawable(R.mipmap.select_normal))
            root!!.im_all_select.tag = "noSelect"
        }
    }

    private fun initView() {
        updateTotalPrice(0.0f)
        root!!.tv_pay.setOnClickListener {
            if (totalPrice > 0) {
                val ordersList = ArrayList<OrderFormBean.ServerDataBean>();
                for (i in 0 until mSelectList.size) {
                    val bean = OrderFormBean.ServerDataBean(mSelectList[i].price, mSelectList[i].objectId, mSelectList[i].title, mSelectList[i].imageUrl, 0, mSelectList[i].shopId)
                    ordersList.add(bean)
                }
                EventBus.getDefault().postSticky(ordersList)
                startActivity(Intent(activity!!, OrderFormCreateActivity::class.java))
            }

        }
        root!!.rvl_shop.setOnRefreshListener(1, object : RefreshableViewList.RefreshCallBack {
            override fun onRefresh() {
                getDataFromCould()
                Thread(Runnable {
                    Thread.sleep(1000)
                    root!!.rvl_shop.finishRefresh()
                }).start()
            }

            override fun onFinished() {
            }

        })
        root!!.ll_login.setOnClickListener {
            startActivity(Intent(activity!!, LoginActivity::class.java))
        }
        root!!.tv_delete.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                if (mSelectList.size == 0)
                    return
                val dialog = CocoDialog(activity!!)
                dialog.setMessage("一定要删除嘛？")
                        .setImageResId(R.mipmap.ic_kiwi)
                        .setTitle("果果提示")
                        .setSingle(false)
                        .setOnClickBottomListener(object : CocoDialog.OnClickBottomListener {
                            override fun onPositiveClick() {//删除
                                deleteSelectedData()
                                dialog.dismiss()
                            }

                            override fun onNegtiveClick() {//没有删除
                                dialog.dismiss()
                            }
                        }).show()

            }
        })
        root!!.ll_all_select.setOnClickListener { v ->
            if (v!!.tag == "select") {//如果原来图片的按钮是全选，也就是说现在的目标是全不选
                v.tag = "noSelect"
                mSelectList.clear()
                for (i in 0 until mList.size) {
                    mList[i].isSelect = false
                }
                totalPrice = 0f
                updateTotalPrice(0f, true)
            } else {//目标是全选
                totalPrice = 0f
                mSelectList.clear()
                v.tag = "select"
                mSelectList.addAll(mList)
                for (i in 0 until mList.size) {
                    mList[i].isSelect = true
                }
                updateTotalPrice(0.0f, true)
            }
            updateOtherButton()
            mAdapt!!.notifyDataSetChanged()
        }
    }

    //删除在数据库中的商品
    private fun removeGoodsFromDb() {
        for (i in 0 until mSelectList.size) {
            mDbHelper!!.delete(Table.ShopCartTable.TABLE_NAME, "goods_id=?", arrayOf(mSelectList[i].objectId))
        }
    }

//    private fun getGoodsFromDb() {
//        mList.clear()
//        val sql = "select * from ${Table.ShopCartTable.TABLE_NAME}"
//        val mCursor: Cursor = mDbHelper!!.exeSql(sql)
//        while (mCursor.moveToNext()) {
//            val bean = HomeFruitBean()
//            bean.shopId = mCursor.getString(mCursor.getColumnIndex(Table.ShopCartTable.SHOP_ID))
//            bean.objectId = mCursor.getString(mCursor.getColumnIndex(Table.ShopCartTable.GOODS_ID))
//            bean.classify = mCursor.getInt(mCursor.getColumnIndex(Table.ShopCartTable.CLASSIFY))
//            bean.price = mCursor.getFloat(mCursor.getColumnIndex(Table.ShopCartTable.PRICE))
//            bean.imageUrl = mCursor.getString(mCursor.getColumnIndex(Table.ShopCartTable.IMAGE_URL))
//            bean.subtitle = mCursor.getString(mCursor.getColumnIndex(Table.ShopCartTable.SUBTITLE))
//            bean.title = mCursor.getString(mCursor.getColumnIndex(Table.ShopCartTable.TITLE))
//            bean.discount = mCursor.getString(mCursor.getColumnIndex(Table.ShopCartTable.DISCOUNT))
//            bean.totalSale = mCursor.getInt(mCursor.getColumnIndex(Table.ShopCartTable.TOTAL_SALE))
//            mList.add(bean)
//        }
//        mAdapt!!.notifyDataSetChanged()
//    }

    private fun setAdaptManager() {
        val layoutManager = LinearLayoutManager(activity!!)
        root!!.rv_shop_car!!.layoutManager = layoutManager
        root!!.rv_shop_car!!.setHasFixedSize(true)
        mAdapt = ShopCarAdapt(mList, activity!!, object : ShopCarAdapt.SelectCallBack {
            override fun back(position: Int, price: Float, isAdd: Boolean) {
                if (isAdd) {//添加
                    mSelectList.add(mList[position])
                    updateTotalPrice(price)
                } else {//删除
                    mSelectList.remove(mList[position])
                    updateTotalPrice(-price)
                }
                updateOtherButton()
            }
        })
        root!!.rv_shop_car!!.adapter = mAdapt
        root!!.rv_shop_car!!.itemAnimator = DefaultItemAnimator()
    }

    private fun initData() {
        val isLogin = SharedPreferencesUtils.getInstance().getData("loginStatus", false) as Boolean
        if (isLogin) {
            root!!.ll_login.visibility = View.GONE
            getDataFromCould()
        } else {
            root!!.ll_login.visibility = View.VISIBLE
        }
    }

    private fun getDataFromCould(){
        SearchShopCartCould(activity!!, SearchShopCartCould.ShopCartInfoBackAboutData { dataList ->
            mList.clear()
            mSelectList.clear()
            for (i in 0 until dataList!!.size){
                val data=dataList[i].serverData
                val bean=HomeFruitBean(data.title,data.subtitle,data.price,data.arriveTime,data.discount,data.imageUrl,data.classify,data.totalSale,dataList[i].objectId)
                bean.isSelect=false
                bean.shopId=dataList[i].serverData.shopId
                mList.add(bean)
            }
            SharedPreferencesUtils.getInstance().saveData("redPointNum",dataList.size)
            EventBus.getDefault().post(MainActivity.RedPointMessageEvent())
            mAdapt!!.notifyDataSetChanged()
        })
    }

    //操作小红点
    @Subscribe
    fun onMessageEvent(msg: MainActivity.RedPointMessageEvent) {

    }

    //传递订单信息给下一个支付界面
    @Subscribe(sticky = true)
    fun onMessageEvent(ordersList: ArrayList<OrderFormBean.ServerDataBean>) {

    }

    //用于结束支付后删除在购物车中的订单
    @Subscribe
    fun onOrderDelete(ordersList: ArrayList<String>) {
        for (i in 0 until mList.size) {
            mList[i].isSelect = false
        }
        totalPrice = 0f
        updateTotalPrice(0f, true)
        mAdapt!!.notifyDataSetChanged()
    }

    fun deleteSelectedData() {
        val deleteList = ArrayList<String>()
        for (i in 0 until mSelectList.size) {
            mList.remove(mSelectList[i])
            deleteList.add(mSelectList[i].shopId)
        }
        DeleteGoodsFromShopCartCloud(activity!!, deleteList)//删除云端的数据
        //小红点中的数据一定要减少
        val redPointNum = SharedPreferencesUtils.getInstance().getData("redPointNum", 0) as Int
        SharedPreferencesUtils.getInstance().saveData("redPointNum", redPointNum - deleteList.size)
        EventBus.getDefault().post(MainActivity.RedPointMessageEvent())
        //已经选择的数据清空，因为已经删了
        mSelectList.clear()
        mAdapt!!.notifyDataSetChanged()
        updateTotalPrice(0f, true)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this)
    }

    override fun onStop() {
        super.onStop()
        mSelectList.clear()
        updateTotalPrice(0f, true)
    }

    override fun onStart() {
        super.onStart()
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this)
    }
}
