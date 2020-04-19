package com.gioppl.fruitmanor.view.activity

import android.content.ContentValues
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.gioppl.fruitmanor.MyApplication
import com.gioppl.fruitmanor.bean.CouponCouldBean
import com.gioppl.fruitmanor.broadcast.MainBroadcastReceiver
import com.gioppl.fruitmanor.databinding.ActivityGoodsCouponGetBinding
import com.gioppl.fruitmanor.net.SearchCouponForFruitCould
import com.gioppl.fruitmanor.net.UpdateCouponStatusCloud
import com.gioppl.fruitmanor.sql.Table
import com.gioppl.fruitmanor.tool.RefreshableViewList
import com.gioppl.fruitmanor.tool.SharedPreferencesUtils
import com.gioppl.fruitmanor.view.adapt.GoodsCouponGetAdapt
import kotlinx.android.synthetic.main.activity_goods_coupon_get.view.*

class GoodsCouponGetActivity : BaseActivity() {
    private var root: View? = null
    private var mList = ArrayList<CouponCouldBean.ServerDataBean>()
    private var mAdapt: GoodsCouponGetAdapt? = null
    private var fruitId = ""
    override fun receiveBroadCast(broadCastClassify: MainBroadcastReceiver.BroadCastClassify, statusCode: Int, msg: Any?) {
        if (broadCastClassify == MainBroadcastReceiver.BroadCastClassify.LOGIN) {
            if (statusCode == MainBroadcastReceiver.STATUS_CODE_0X01) {//已经登陆
                root!!.ll_login.visibility = View.GONE
            } else {
                root!!.ll_login.visibility = View.VISIBLE
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mBinding = ActivityGoodsCouponGetBinding.inflate(layoutInflater)
        setContentView(mBinding!!.root)
        root = mBinding.root
        setAdaptManager()
        initView()
        initData()
    }

    private fun initView() {
        root!!.rl_coupon.setOnRefreshListener(1, object : RefreshableViewList.RefreshCallBack {
            override fun onRefresh() {
                Thread(Runnable {
                    Thread.sleep(1000)
                    root!!.rl_coupon.finishRefresh()
                }).start()
            }

            override fun onFinished() {
            }

        })
        root!!.ll_login.setOnClickListener {
            startActivity(Intent(this@GoodsCouponGetActivity, LoginActivity::class.java))
        }
    }

    private fun setAdaptManager() {
        val layoutManager = LinearLayoutManager(this)
        root!!.rv_coupon!!.layoutManager = layoutManager
        root!!.rv_coupon!!.setHasFixedSize(true)
        mAdapt = GoodsCouponGetAdapt(mList, this, object : GoodsCouponGetAdapt.CouponClickCallBack {
            override fun getCouponMsg(position: Int) {
                val isLogin = SharedPreferencesUtils.getInstance().getData("loginStatus", false) as Boolean
                if (isLogin) {
                    mango(this@GoodsCouponGetActivity, "获取成功")
                    UpdateCouponStatusCloud(this@GoodsCouponGetActivity, mList[position].objectId, 0)
                    mAdapt!!.notifyDataSetChanged()
                    saveToDb(mList[position])
                    mList.removeAt(position)
                } else {
                    mango(this@GoodsCouponGetActivity, "获取失败，请先登陆")
                    startActivity(Intent(this@GoodsCouponGetActivity, LoginActivity::class.java))
                }
            }
        })
        root!!.rv_coupon!!.adapter = mAdapt
        root!!.rv_coupon!!.itemAnimator = DefaultItemAnimator()
    }

    private fun saveToDb(data: CouponCouldBean.ServerDataBean) {
        val db: SQLiteDatabase = MyApplication.getInstance().dbHelper.writableDatabase
        db.beginTransaction()
        val values = ContentValues()
        values.put(Table.CouponTable.OBJECT_ID, data.objectId)
        values.put(Table.CouponTable.GOODS_ID, data.goods_id)
        values.put(Table.CouponTable.REDUCE_MONEY, data.reduce_money)
        values.put(Table.CouponTable.END_TIME, data.endTime)
        values.put(Table.CouponTable.TITLE, data.title)
        values.put(Table.CouponTable.IMAGE_URL, data.imageUrl)
        db.replace(Table.CouponTable.TABLE_NAME, null, values)
        db.setTransactionSuccessful()
        db.endTransaction()
        db.close()
    }

    private fun initData() {
        val isLogin = SharedPreferencesUtils.getInstance().getData("loginStatus", false) as Boolean
        if (isLogin) {
            root!!.ll_login.visibility = View.GONE
        } else {
            root!!.ll_login.visibility = View.VISIBLE
        }
        fruitId = intent.getStringExtra("fruitId");
        SearchCouponForFruitCould(this, fruitId, object : SearchCouponForFruitCould.CouponsBack {
            override fun couponsBack(coupons: java.util.ArrayList<CouponCouldBean>?) {
                mList.clear();
                for (i in 0 until coupons!!.size) {
                    val bean = coupons[i].serverData
                    bean.objectId = coupons[i].objectId
                    mList.add(bean)
                }

                mAdapt!!.notifyDataSetChanged()
            }

        })
    }

    fun back(view: View) {
        finish()
    }
}