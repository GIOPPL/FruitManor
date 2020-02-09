package com.gioppl.fruitmanor.view.activity

import android.database.Cursor
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gioppl.fruitmanor.MyApplication
import com.gioppl.fruitmanor.R
import com.gioppl.fruitmanor.bean.CouponBean
import com.gioppl.fruitmanor.broadcast.MainBroadcastReceiver
import com.gioppl.fruitmanor.sql.MyDbHelper
import com.gioppl.fruitmanor.sql.Table
import com.gioppl.fruitmanor.tool.RefreshableViewList
import com.gioppl.fruitmanor.view.adapt.CouponAdapt

class CouponActivity :BaseActivity(){
    private var rvl: RefreshableViewList? = null
    private var mList = ArrayList<CouponBean>()
    private var mRV: RecyclerView? = null
    private var mAdapt: CouponAdapt? = null
    private var mDbHelper: MyDbHelper? = null
    override fun receiveBroadCast(broadCastClassify: MainBroadcastReceiver.BroadCastClassify?, statusCode: Int, msg: Any?) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coupon)
        mDbHelper= MyApplication.getInstance().dbHelper
        initView()
        initData()
    }

    private fun initData() {
        getGoodsFromDb()
    }
    private  fun getGoodsFromDb(){
        mList.clear()
        val sql="select * from ${Table.CouponTable.TABLE_NAME}"
        strawberry(this,"查询数据库的优惠券：$sql")
        val mCursor: Cursor = mDbHelper!!.exeSql(sql)
        while (mCursor.moveToNext()) {
            val bean= CouponBean()
            bean.goods_id=mCursor.getString(mCursor.getColumnIndex(Table.CouponTable.GOODS_ID))
            bean.reduce_money=mCursor.getInt(mCursor.getColumnIndex(Table.CouponTable.REDUCE_MONEY))
            bean.imageUrl=mCursor.getString(mCursor.getColumnIndex(Table.CouponTable.IMAGE_URL))
            bean.title=mCursor.getString(mCursor.getColumnIndex(Table.CouponTable.TITLE))
            bean.endTime=mCursor.getString(mCursor.getColumnIndex(Table.CouponTable.END_TIME))
            mList.add(bean)
        }
        mAdapt!!.notifyDataSetChanged()
    }

    private fun initView() {
        mRV = findViewById(R.id.rv_coupon)
        val layoutManager = LinearLayoutManager(this)
        mRV!!.layoutManager = layoutManager
        mRV!!.setHasFixedSize(true)
        mAdapt = CouponAdapt(mList, this, object : CouponAdapt.CouponClickCallBack{
            override fun lookDescription(position: Int) {

            }
        })
        mRV!!.setAdapter(mAdapt)
        mRV!!.setItemAnimator(DefaultItemAnimator())
    }

    fun back(view: View) {
        finish()
    }
}