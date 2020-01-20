package com.gioppl.fruitmanor.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gioppl.fruitmanor.R
import com.gioppl.fruitmanor.bean.HomeFruitBean
import com.gioppl.fruitmanor.tool.CocoDialog
import com.gioppl.fruitmanor.tool.CocoDialog.OnClickBottomListener
import com.gioppl.fruitmanor.tool.RectBackGrand
import com.gioppl.fruitmanor.view.adapt.ShopCarAdapt
import java.util.*
import kotlin.collections.ArrayList


/**
 * Created by GIOPPL on 2017/10/8.
 */
class ShopCarFragment2 : Fragment() {

    var mList = ArrayList<HomeFruitBean>()
    var mDelectList = ArrayList<Int>()
    var mSelectList=ArrayList<Int>()
    var mRV: RecyclerView? = null
    var mAdapt: ShopCarAdapt? = null

    var ll_login: LinearLayout? = null
    var ll_all_select: LinearLayout? = null
    var tv_all_price: TextView? = null
    var tv_pay: TextView? = null
    var tv_delect: TextView? = null
    var im_all_select: ImageView? = null
    var tv_msg: TextView? = null

    var isAllSelect = false
    var isDelete = false

    var allPrice = 0.0f


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_shop_car, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
        for (i in 0..10) {
            val bean = HomeFruitBean("$i", "黄金果肉 咬上一口 香甜细糯", 29.9f,
                    "次日达", "7折", "http://lc-d2693j76.cn-n1.lcfile.com/52fb246cdb7d2b90006b/cheer.jpg",
                    0, 100, "11111");
            bean.isSelect = false;
            mList.add(bean)
        }
        setAdaptManager()
        mAdapt!!.notifyDataSetChanged()
    }


    private fun initView() {
        ll_login = activity!!.findViewById(R.id.ll_login)
        ll_all_select = activity!!.findViewById(R.id.ll_all_select)
        tv_all_price = activity!!.findViewById(R.id.tv_all_price)
        tv_pay = activity!!.findViewById(R.id.tv_pay)
        tv_delect = activity!!.findViewById(R.id.tv_delect)
        im_all_select = activity!!.findViewById(R.id.im_all_select)
        tv_msg = activity!!.findViewById(R.id.tv_msg)

        ll_login!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {

            }
        })
        ll_all_select!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                isAllSelect = !isAllSelect
                if (!isDelete) {//非删除模式
                    if (isAllSelect) {
                        allPrice = 0.0f
                        im_all_select!!.setImageDrawable(activity!!.getDrawable(R.mipmap.select_pressed))
                        for (i in 0 until mList.size) {
                            mList[i].isSelect = true
                            allPrice += mList[i].price
                        }
                    } else {
                        allPrice = 0.0f
                        im_all_select!!.setImageDrawable(activity!!.getDrawable(R.mipmap.select_normal))
                        for (i in 0 until mList.size) {
                            mList[i].isSelect = false
                        }
                    }
                    mAdapt!!.notifyDataSetChanged()
                    val s = "￥" + String.format("%.2f", allPrice)
                    if (s == "￥-0.00" || s == "￥0.00") {
                        tv_all_price!!.text = "￥0"
                    } else {
                        tv_all_price!!.text = s
                    }
                    if (allPrice > 0.0) {
                        tv_pay!!.setTextColor(activity!!.resources.getColor(R.color.white))
                        tv_pay!!.background = activity!!.getDrawable(R.drawable.rect_second_color_10dp)
                    } else {
                        tv_pay!!.setTextColor(activity!!.resources.getColor(R.color.noFocusColor))
                        tv_pay!!.background = activity!!.getDrawable(R.drawable.rect_grey_10dp)
                    }
                } else {

                    if (isAllSelect) {
                        im_all_select!!.setImageDrawable(activity!!.getDrawable(R.mipmap.select_pressed))
                        for (i in 0 until mList.size) {
                            mDelectList.add(i)
                            mList[i].isSelect = true
                        }
                    } else {
                        im_all_select!!.setImageDrawable(activity!!.getDrawable(R.mipmap.select_normal))
                        for (i in 0 until mList.size) {
                            mList[i].isSelect = false
                        }
                        mDelectList.clear()
                    }
                    mAdapt!!.notifyDataSetChanged()


                    for (i in 0 until mList.size) {

                        mList[i].isSelect = true
                    }
                    mAdapt!!.notifyDataSetChanged()

                }

            }
        })
        tv_pay!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                if (isDelete) {//删除模式
                    val dialog = CocoDialog(activity!!)
                    dialog.setMessage("一定要删除嘛？")
                            .setImageResId(R.mipmap.ic_kiwi)
                            .setTitle("果果提示")
                            .setSingle(false)
                            .setOnClickBottomListener(object : OnClickBottomListener {
                                override fun onPositiveClick() {
                                    allPrice=0.0f
                                    tv_all_price!!.text = "￥0"
                                    im_all_select!!.setImageDrawable(activity!!.getDrawable(R.mipmap.select_normal))
                                    Collections.sort(mDelectList);
                                    for (i in mDelectList.size - 1 downTo 0) {
                                        mList[mDelectList[i]].isSelect = false
                                        mAdapt!!.notifyItemRemoved(mDelectList[i])
                                    }
                                    for (i in mDelectList.size - 1 downTo 0) {
                                        mList.removeAt(mDelectList[i])
                                    }
                                    mDelectList.clear()
                                    mAdapt!!.notifyDataSetChanged()

                                    dialog.dismiss()
                                }

                                override fun onNegtiveClick() {
                                    dialog.dismiss()
                                }
                            }).show()
                } else {//结算模式

                }
            }
        })
        tv_delect!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                mDelectList.clear()
                isDelete = !isDelete
                for (i in 0 until mList.size) {
                    mList[i].isSelect = false
                }
                mAdapt!!.notifyDataSetChanged()
                tv_all_price!!.text = "￥0"
                if (isDelete) {//进入删除模式
                    isAllSelect = false
                    allPrice = 0.0f
                    im_all_select!!.setImageDrawable(activity!!.getDrawable(R.mipmap.select_normal))
                    tv_delect!!.setTextColor(ContextCompat.getColor(activity!!, R.color.white))
                    tv_delect!!.background = RectBackGrand.kaleidoscope(60f, ContextCompat.getColor(activity!!, R.color.secondColor))
                    tv_all_price!!.visibility = View.GONE
                    tv_msg!!.text = "0件待删除商品"
                    tv_pay!!.text = "点击删除"
                    tv_pay!!.setTextColor(ContextCompat.getColor(activity!!, R.color.white))
                    tv_pay!!.background = activity!!.resources.getDrawable(R.drawable.rect_red_10dp)

                } else {
                    tv_delect!!.setTextColor(ContextCompat.getColor(activity!!, R.color.black))
                    tv_delect!!.setBackgroundColor(ContextCompat.getColor(activity!!, R.color.white))
                    tv_all_price!!.visibility = View.VISIBLE
                    tv_msg!!.text = "不含运费 合计:"
                    tv_pay!!.text = "去结算"
                    tv_pay!!.setTextColor(ContextCompat.getColor(activity!!, R.color.noFocusColor))
                    tv_pay!!.background = activity!!.resources.getDrawable(R.drawable.rect_grey_10dp)
                }

            }
        })

    }

    private fun setAdaptManager() {
        mRV = activity!!.findViewById(R.id.rv_shop_car)
        val layoutManager = LinearLayoutManager(context)
        mRV!!.layoutManager = layoutManager
        mRV!!.setHasFixedSize(true)
        mAdapt = ShopCarAdapt(mList, context!!, object : ShopCarAdapt.SelectCallBack {
            override fun back(position: Int, price: Float, isAdd: Boolean) {
                if (isAdd)
                    allPrice += price
                else
                    allPrice -= price


                val s = "￥" + String.format("%.2f", allPrice)
                if (s == "￥-0.00" || s == "￥0.00") {
                    tv_all_price!!.text = "￥0"
                } else {
                    tv_all_price!!.text = s
                }


                if (allPrice > 0.0) {
                    if (isDelete) {
                        tv_pay!!.setTextColor(activity!!.resources.getColor(R.color.white))
                        tv_pay!!.background = activity!!.getDrawable(R.drawable.rect_red_10dp)
                    } else {
                        tv_pay!!.setTextColor(activity!!.resources.getColor(R.color.white))
                        tv_pay!!.background = activity!!.getDrawable(R.drawable.rect_second_color_10dp)
                    }

                } else {
                    tv_pay!!.setTextColor(activity!!.resources.getColor(R.color.noFocusColor))
                    tv_pay!!.background = activity!!.getDrawable(R.drawable.rect_grey_10dp)
                }

                if (isDelete) {
                    if (isAdd) {
                        mDelectList.add(position)
                    } else {
                        mDelectList.remove(position)
                    }
                    tv_msg!!.text = "${mDelectList.size}件待删除商品"
                }
            }
        })
        mRV!!.setAdapter(mAdapt)
        mRV!!.setItemAnimator(DefaultItemAnimator())
    }
}