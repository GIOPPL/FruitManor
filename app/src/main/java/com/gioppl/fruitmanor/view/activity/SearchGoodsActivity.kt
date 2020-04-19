package com.gioppl.fruitmanor.view.activity

import android.content.Intent
import android.graphics.Point
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gioppl.fruitmanor.MyApplication
import com.gioppl.fruitmanor.R
import com.gioppl.fruitmanor.bean.HomeFruitBean
import com.gioppl.fruitmanor.bean.NetFruitBean
import com.gioppl.fruitmanor.broadcast.MainBroadcastReceiver
import com.gioppl.fruitmanor.net.AddGoodsToShopCartCloud
import com.gioppl.fruitmanor.net.SearchFruitByUserCould
import com.gioppl.fruitmanor.sql.MyDbHelper
import com.gioppl.fruitmanor.tool.RefreshableViewList
import com.gioppl.fruitmanor.tool.SharedPreferencesUtils
import com.gioppl.fruitmanor.view.adapt.HomeFruitAdapt
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

//商品查询的界面
class SearchGoodsActivity : BaseActivity() {
    private var rvl: RefreshableViewList? = null
    var moreInfoList= java.util.ArrayList<NetFruitBean>()//包含全部信息的列表
    private var mList = ArrayList<HomeFruitBean>()
    private var mRV: RecyclerView? = null
    private var mAdapt: HomeFruitAdapt? = null
    private var mDbHelper: MyDbHelper? = null
    override fun receiveBroadCast(broadCastClassify: MainBroadcastReceiver.BroadCastClassify?, statusCode: Int, msg: Any?) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goods_search)
        mDbHelper = MyApplication.getInstance().dbHelper
        initView()
        initData()
    }

    private fun initData() {
        var searchKey=intent.getStringExtra("searchKey")
        searchKey=searchKey.replace("\n","")
        getGoodsFromCloud(searchKey)
    }

    private fun getGoodsFromCloud(searchKey:String) {
        SearchFruitByUserCould(this,searchKey,object : SearchFruitByUserCould.NetData{
            override fun getData(beanList: java.util.ArrayList<NetFruitBean>?) {
                if (beanList==null||beanList.size==0){
                    Mango(this@SearchGoodsActivity,"无查询结果")
                }else{
                    moreInfoList.clear()
                    moreInfoList.addAll(beanList)
                    mList.clear();
                    for (i in beanList) {
                        val bean = HomeFruitBean(i.serverData.title, i.serverData.subtitle, i.serverData.price.toFloat(),
                                i.serverData.arriveTime, i.serverData.discount, i.serverData.imageUrl,
                                i.serverData.classify, i.serverData.totalSale, i.objectId)
                        mList.add(bean);
                    }
                    mAdapt!!.notifyDataSetChanged();
                }
            }
        })
    }

    private fun initView() {
        mRV = findViewById(R.id.rvl_search)
        val layoutManager = LinearLayoutManager(this)
        mRV!!.layoutManager = layoutManager
        mRV!!.setHasFixedSize(true)
        mAdapt = HomeFruitAdapt(mList, this, object : HomeFruitAdapt.HomeClickCallBack {
            override fun addToShopCar(imageView: ImageView, point: Point, position: Int) {
                val isLogin = SharedPreferencesUtils.getInstance().getData("loginStatus", false) as Boolean
                if (isLogin) {//已经登陆了
                    //联网操作
                    val userPhone = SharedPreferencesUtils.getInstance().getData("phoneNumber", "") as String
                    val fruitId = mList[position].objectId
                    AddGoodsToShopCartCloud(this@SearchGoodsActivity, userPhone, fruitId)
                    val redPointNum = SharedPreferencesUtils.getInstance().getData("redPointNum", 0) as Int
                    SharedPreferencesUtils.getInstance().saveData("redPointNum", redPointNum + 1)
                    EventBus.getDefault().post(MainActivity.RedPointMessageEvent())
                    mango(this@SearchGoodsActivity, "添加成功")
                } else {
                    mango(this@SearchGoodsActivity, "请先登陆")
                    startActivity(Intent(this@SearchGoodsActivity, LoginActivity::class.java))
                }
            }
            override fun lookDescription(position: Int) {
                startActivity(Intent(this@SearchGoodsActivity,GoodsLookActivity::class.java))
                EventBus.getDefault().postSticky(moreInfoList[position])
            }

        })
        mRV!!.adapter = mAdapt
        mRV!!.itemAnimator = DefaultItemAnimator()
    }

    fun back(view: View) {
        finish()
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