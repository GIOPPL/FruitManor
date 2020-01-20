package com.gioppl.fruitmanor.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gioppl.fruitmanor.R
import com.gioppl.fruitmanor.bean.HomeFruitBean
import com.gioppl.fruitmanor.bean.NetFruitBean
import com.gioppl.fruitmanor.net.SearchFruitMassageCould
import com.gioppl.fruitmanor.view.adapt.ClassifyDescriptionAdapt


class ClassifyFragmentDescription() : Fragment() {
    private var rv: RecyclerView? = null;
    private var mAdapt: ClassifyDescriptionAdapt? = null
    private val mainList=ArrayList<HomeFruitBean>()
    private var mList=ArrayList<HomeFruitBean>()
    private var position=0;
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.classify_description_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initDate()
        initView()
    }
    private fun initDate() {
        getData()
    }



    private fun initView() {
        rv = activity!!.findViewById(R.id.rv_description)
        val layoutManager = LinearLayoutManager(context)
        rv!!.layoutManager = layoutManager
        rv!!.setHasFixedSize(true)
        mAdapt = ClassifyDescriptionAdapt(mList, context!!)
        rv!!.adapter = mAdapt
        rv!!.itemAnimator = DefaultItemAnimator()

    }

    private fun getData() {
        val leanCouldNet= SearchFruitMassageCould(activity!!, object : SearchFruitMassageCould.NetData {
            override fun getData(beanList: java.util.ArrayList<NetFruitBean>?) {
                mList.clear();
                for (i in beanList!!) {
                    val bean = HomeFruitBean(i.serverData.title, i.serverData.subtitle, i.serverData.price.toFloat(),
                            i.serverData.arriveTime, i.serverData.discount, i.serverData.imageUrl,i.serverData.classify,i.serverData.totalSale)
                    mList.add(bean);
                    mainList.add(bean)
                }

                mAdapt!!.notifyDataSetChanged();
            }
        });
        leanCouldNet.getNetDate();
    }
    public fun all(){
        mList.clear()
        mList.addAll(mainList)
        mAdapt!!.notifyDataSetChanged()
    }
    public fun synthesize(){
        refreshData(position)
    }
    public fun totalSale(){
        mList.sortByDescending { it.totalSale }
        mAdapt!!.notifyDataSetChanged()
    }
    public fun sortPrice(downSort:Boolean){
        if (downSort){
            mList.sortByDescending { it.price }
        }else{
            mList.sortBy { it.price }
        }
        mAdapt!!.notifyDataSetChanged()
    }

    public fun refreshData(position:Int){
        mList.clear();
        for (i in 0 until mainList.size){
            if (mainList[i].classify==position)
                mList.add(mainList[i])
        }
        mAdapt!!.notifyDataSetChanged()
        this.position=position
    }
}