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
import com.gioppl.fruitmanor.view.adapt.HomeFruitAdapt


/**
 * Created by GIOPPL on 2017/10/8.
 */
class HomeFragment : Fragment() {
    var mList=ArrayList<HomeFruitBean>()
    var mRV: RecyclerView? = null
    var mAdapt: HomeFruitAdapt? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        getData()

//        NetFileTools.getInstance().upload("background")

//        val uploadFruitMassageCould=UploadFruitMassageCould(activity!!,object : UploadFruitMassageCould.NetData{
//            override fun getData(msg: String?) {
//
//            }
//
//        });
//        val bean=HomeFruitBean("四川黄心猕猴桃(单果70-90g)","黄金果肉 咬上一口 香甜细糯",29.9f,"次日达","7折","http://lc-d2693j76.cn-n1.lcfile.com/52fb246cdb7d2b90006b/cheer.jpg");
//        uploadFruitMassageCould.uploadMassage(bean);

        setAdaptManager()
    }

    private fun getData() {
        val leanCouldNet= SearchFruitMassageCould(activity!!, object : SearchFruitMassageCould.NetData {
            override fun getData(beanList: java.util.ArrayList<NetFruitBean>?) {
                mList.clear();
                for (i in beanList!!) {
                    val bean = HomeFruitBean(i.serverData.title, i.serverData.subtitle, i.serverData.price.toFloat(), i.serverData.arriveTime, i.serverData.discount, i.serverData.imageUrl)
                    mList.add(bean);
                }
                mAdapt!!.notifyDataSetChanged();
            }
        });
        leanCouldNet.getNetDate();
    }

    private fun setAdaptManager() {
        mRV=activity!!.findViewById(R.id.rv_policy)
        val layoutManager = LinearLayoutManager(context)
        mRV!!.layoutManager=layoutManager
        mRV!!.setHasFixedSize(true)
        mAdapt = HomeFruitAdapt(mList, context!!)
        mRV!!.setAdapter(mAdapt)
        mRV!!.setItemAnimator(DefaultItemAnimator())
    }
}
