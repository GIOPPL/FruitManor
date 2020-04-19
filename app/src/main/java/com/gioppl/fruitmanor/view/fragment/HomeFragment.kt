package com.gioppl.fruitmanor.view.fragment

import android.content.ContentValues
import android.content.Intent
import android.graphics.Point
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gioppl.fruitmanor.MyApplication
import com.gioppl.fruitmanor.R
import com.gioppl.fruitmanor.animation.ShoppingCartAnimation
import com.gioppl.fruitmanor.bean.HomeFruitBean
import com.gioppl.fruitmanor.bean.NetFruitBean
import com.gioppl.fruitmanor.broadcast.MainBroadcastReceiver
import com.gioppl.fruitmanor.net.AddGoodsToShopCartCloud
import com.gioppl.fruitmanor.net.SearchFruitMassageCould
import com.gioppl.fruitmanor.sql.Table
import com.gioppl.fruitmanor.tool.SharedPreferencesUtils
import com.gioppl.fruitmanor.view.activity.*
import com.gioppl.fruitmanor.view.adapt.HomeFruitAdapt
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe


class HomeFragment : BaseFragment(), HomeFruitAdapt.HomeClickCallBack {
    var mList = ArrayList<HomeFruitBean>()
    var moreInfoList= java.util.ArrayList<NetFruitBean>()//包含全部信息的列表
    var mRV: RecyclerView? = null
    var mAdapt: HomeFruitAdapt? = null
    var rl_home: RelativeLayout? = null
    var et_search: EditText? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun receiveBroadCast(broadCastClassify: MainBroadcastReceiver.BroadCastClassify, statusCode: Int, msg: Any?) {

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        getData()
        setAdaptManager()
        initView()
    }

    private fun initView() {
        et_search = activity!!.findViewById(R.id.et_search)
        et_search!!.setOnEditorActionListener(object : TextView.OnEditorActionListener{
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_SEND
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || (event != null && KeyEvent.KEYCODE_ENTER == event.getKeyCode() && KeyEvent.ACTION_DOWN == event.getAction())) {
                    //处理事件
                    val s=et_search!!.text.toString()
                    et_search!!.text.clear()
                    val intent =Intent(activity!!,SearchGoodsActivity::class.java)
                    intent.putExtra("searchKey",s)
                    startActivity(intent)
                }
                return false;
            }

        })
    }

    private fun getData() {
        val leanCouldNet = SearchFruitMassageCould(activity!!, object : SearchFruitMassageCould.NetData {
            override fun getData(beanList: java.util.ArrayList<NetFruitBean>?) {
                moreInfoList.clear()
                moreInfoList.addAll(beanList!!)
                mList.clear();
                for (i in beanList) {
                    val bean = HomeFruitBean(i.serverData.title, i.serverData.subtitle, i.serverData.price.toFloat(),
                            i.serverData.arriveTime, i.serverData.discount, i.serverData.imageUrl,
                            i.serverData.classify, i.serverData.totalSale, i.objectId)
                    mList.add(bean);
                }
                mAdapt!!.notifyDataSetChanged();
            }
        });
        leanCouldNet.getNetDate();
    }

    private fun setAdaptManager() {
        rl_home = activity!!.findViewById(R.id.ll_home)
        mRV = activity!!.findViewById(R.id.rv_home)
        val layoutManager = LinearLayoutManager(context)
        mRV!!.layoutManager = layoutManager
        mRV!!.setHasFixedSize(true)
        mAdapt = HomeFruitAdapt(mList, context!!, this)
        mRV!!.adapter = mAdapt
        mRV!!.itemAnimator = DefaultItemAnimator()
    }

    override fun addToShopCar(imageView: ImageView, point: Point, position: Int) {
        val isLogin = SharedPreferencesUtils.getInstance().getData("loginStatus", false) as Boolean
        if (isLogin) {//已经登陆了
            //联网操作
            val userPhone = SharedPreferencesUtils.getInstance().getData("phoneNumber", "") as String
            val fruitId = mList[position].objectId
            AddGoodsToShopCartCloud(activity!!, userPhone, fruitId)
            val shoppingCart = activity!!.findViewById(R.id.rbtn_main_three) as RadioButton
            val shoppingCartAnimation = ShoppingCartAnimation(activity)
            val cartIcon = ImageView(activity)
            cartIcon.layoutParams = ViewGroup.LayoutParams(80, 80)
            cartIcon.setImageDrawable(imageView.drawable)
            shoppingCartAnimation.addGoodsToCart(cartIcon, imageView, shoppingCart)
            //添加到本地数据库
//            saveToDb()
            val redPointNum = SharedPreferencesUtils.getInstance().getData("redPointNum", 0) as Int
            SharedPreferencesUtils.getInstance().saveData("redPointNum", redPointNum + 1)
            EventBus.getDefault().post(MainActivity.RedPointMessageEvent())
//             LoginStatusChangedTools(activity!!)
        } else {
            BaseActivity.Mango(activity!!, "请先登陆")
            startActivity(Intent(activity!!, LoginActivity::class.java))
        }
    }

    //将商品添加到本地数据库
    private fun saveToDb(data: NetFruitBean.ServerDataBean, shopId: String): Unit {
        val db = MyApplication.getInstance().getDbHelper().getWritableDatabase()
        db.beginTransaction()
        val values = ContentValues()
        values.put(Table.ShopCartTable.SHOP_ID, shopId)
        values.put(Table.ShopCartTable.GOODS_ID, shopId)
        values.put(Table.ShopCartTable.CLASSIFY, data.classify)
        values.put(Table.ShopCartTable.PRICE, data.price)
        values.put(Table.ShopCartTable.IMAGE_URL, data.imageUrl)
        values.put(Table.ShopCartTable.SUBTITLE, data.subtitle)
        values.put(Table.ShopCartTable.DISCOUNT, data.discount)
        values.put(Table.ShopCartTable.TITLE, data.title)
        values.put(Table.ShopCartTable.TOTAL_SALE, data.totalSale)
        db.replace(Table.ShopCartTable.TABLE_NAME, null, values)
        db.setTransactionSuccessful()
        db.endTransaction()
        db.close()
    }
    override fun lookDescription(position: Int) {
        startActivity(Intent(activity!!,GoodsLookActivity::class.java))
        EventBus.getDefault().postSticky(moreInfoList[position])
    }

    //用于增加小红点数字
    @Subscribe
    fun onMessageEvent(msg: MainActivity.RedPointMessageEvent) {

    }

    @Subscribe(sticky = true)
    fun onMessageEvent2(fruitBean : NetFruitBean) {
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
