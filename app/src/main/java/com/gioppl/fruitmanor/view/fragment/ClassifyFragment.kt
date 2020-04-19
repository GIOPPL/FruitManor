package com.gioppl.fruitmanor.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gioppl.fruitmanor.R
import com.gioppl.fruitmanor.bean.FruitSortBean
import com.gioppl.fruitmanor.broadcast.MainBroadcastReceiver
import com.gioppl.fruitmanor.net.SearchFruitSortCould
import com.gioppl.fruitmanor.view.activity.SearchGoodsActivity
import com.gioppl.fruitmanor.view.adapt.ClassifyAdapt
import java.util.*
class ClassifyFragment : BaseFragment() {
    private val mList = ArrayList<ClassifyAdapt.ClassifyButtonInfo>();
    private var rv: RecyclerView? = null;
    private var mAdapt: ClassifyAdapt? = null
    private var fragment:ClassifyFragmentDescription?=null

    private var tv_all:TextView?=null
    private var tv_sort_synthesize:TextView?=null
    private var tv_total_sale:TextView?=null
    private var tv_sort_price:TextView?=null
    private var im_sort_price: ImageView?=null
    private var view_select:View?=null
    private var sortDown=false

    var et_search: EditText? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_classify, container, false)
    }

    override fun receiveBroadCast(broadCastClassify: MainBroadcastReceiver.BroadCastClassify, statusCode: Int, msg: Any?) {

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initDate()
        initView()

    }


    private fun getGoodsSort() {
        val leanCouldNet= SearchFruitSortCould(activity!!, object : SearchFruitSortCould.NetData {
            override fun getData(beanList: ArrayList<FruitSortBean>?) {
                mList.clear()
                for (i in 0 until  beanList!!.size){
                    val bean=beanList[i].serverData;
                    mList.add(ClassifyAdapt.ClassifyButtonInfo(text = bean.sort,classifyNum = bean.classifyNum))
                }
                mAdapt!!.notifyDataSetChanged();
            }
        });
        leanCouldNet.getNetDate();
    }


    private fun initDate() {
        getGoodsSort()
    }

    private fun initViewColor(){
        tv_all!!.setTextColor(activity!!.resources.getColor(R.color.noFocusColor))
        tv_sort_synthesize!!.setTextColor(activity!!.resources.getColor(R.color.noFocusColor))
        tv_sort_price!!.setTextColor(activity!!.resources.getColor(R.color.noFocusColor))
        tv_total_sale!!.setTextColor(activity!!.resources.getColor(R.color.noFocusColor))
        im_sort_price!!.setImageDrawable(activity!!.getDrawable(R.mipmap.sort_normal))
        view_select!!.setBackgroundColor(activity!!.resources.getColor(android.R.color.white))
    }

    private fun initView() {
        tv_all=activity!!.findViewById(R.id.tv_all);
        tv_sort_price=activity!!.findViewById(R.id.tv_sort_price);
        tv_sort_synthesize=activity!!.findViewById(R.id.tv_sort_synthesize);
        tv_total_sale=activity!!.findViewById(R.id.tv_total_sale);
        im_sort_price=activity!!.findViewById(R.id.im_sort_price);
        view_select=activity!!.findViewById(R.id.view_select);

        et_search = activity!!.findViewById(R.id.et_search)
        et_search!!.setOnEditorActionListener(object : TextView.OnEditorActionListener{
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_SEND
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || (event != null && KeyEvent.KEYCODE_ENTER == event.getKeyCode() && KeyEvent.ACTION_DOWN == event.getAction())) {
                    //处理事件
                    val s=et_search!!.text.toString()
                    et_search!!.text.clear()
                    val intent = Intent(activity!!, SearchGoodsActivity::class.java)
                    intent.putExtra("searchKey",s)
                    startActivity(intent)
                }
                return false;
            }

        })

        tv_all!!.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                initViewColor()
                tv_all!!.setTextColor(activity!!.resources.getColor(R.color.secondColor))
                tv_sort_synthesize!!.setTextColor(activity!!.resources.getColor(R.color.secondColor))
                fragment!!.all()
            }
        })
        tv_sort_synthesize!!.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                initViewColor()
                tv_all!!.setTextColor(activity!!.resources.getColor(R.color.secondColor))
                tv_sort_synthesize!!.setTextColor(activity!!.resources.getColor(R.color.secondColor))
                fragment!!.synthesize()
            }

        })
        tv_total_sale!!.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                initViewColor()
                tv_total_sale!!.setTextColor(activity!!.resources.getColor(R.color.secondColor))
                fragment!!.totalSale()
            }

        })
        tv_sort_price!!.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                initViewColor()
                tv_sort_price!!.setTextColor(activity!!.resources.getColor(R.color.secondColor))
                if (sortDown){
                    im_sort_price!!.setImageDrawable(activity!!.getDrawable(R.mipmap.sort_down))
                    fragment!!.sortPrice(true)
                }else{
                    im_sort_price!!.setImageDrawable(activity!!.getDrawable(R.mipmap.sort_up))
                    fragment!!.sortPrice(false)
                }
                sortDown=!sortDown

            }

        })
        im_sort_price!!.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                initViewColor()
                tv_sort_price!!.setTextColor(activity!!.resources.getColor(R.color.secondColor))
                if (sortDown){
                    im_sort_price!!.setImageDrawable(activity!!.getDrawable(R.mipmap.sort_down))
                }else{
                    im_sort_price!!.setImageDrawable(activity!!.getDrawable(R.mipmap.sort_up))
                }
                sortDown=!sortDown
            }

        })
        view_select!!.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                initViewColor()
                view_select!!.setBackgroundColor(activity!!.resources.getColor(R.color.firstColor))
            }

        })

        rv = activity!!.findViewById(R.id.rv_classify)
        val layoutManager = LinearLayoutManager(context)
        rv!!.layoutManager = layoutManager
        rv!!.setHasFixedSize(true)
        mAdapt = ClassifyAdapt(mList, context!!,object : ClassifyAdapt.ClickBack{
            override fun back(position: Int) {
                for (i in 0 until mList.size){
                    mList[i].flagColor=android.R.color.white
                    mList[i].textColor=android.R.color.black
                }
                mList[position].flagColor=R.color.firstColor
                mList[position].textColor=R.color.secondColor
                mAdapt!!.notifyDataSetChanged()
                fragment!!.refreshData(position)
            }
        })
        rv!!.adapter = mAdapt
        rv!!.itemAnimator = DefaultItemAnimator()

        fragment=ClassifyFragmentDescription()
        val fragmentTransaction= childFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_description, fragment!!)
        fragmentTransaction.commit()

    }
}
