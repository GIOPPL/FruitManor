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
import com.gioppl.fruitmanor.view.adapt.ClassifyAdapt
import java.util.*

/**
 * Created by GIOPPL on 2017/10/8.
 */
class ClassifyFragment : Fragment() {
    private val mList = ArrayList<ClassifyAdapt.ClassifyButtonInfo>();
    var rv: RecyclerView? = null;
    var mAdapt: ClassifyAdapt? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_classify, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initDate()
        initView()
    }

    private fun initDate() {
        mList.add(ClassifyAdapt.ClassifyButtonInfo(text = "全部",flagColor = R.color.firstColor,textColor = R.color.secondColor));
        mList.add(ClassifyAdapt.ClassifyButtonInfo(text = "草莓"))
        mList.add(ClassifyAdapt.ClassifyButtonInfo(text = "椰子"))
        mList.add(ClassifyAdapt.ClassifyButtonInfo(text = "李子"))
        mList.add(ClassifyAdapt.ClassifyButtonInfo(text = "西瓜"))
    }

    private fun initView() {
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
            }
        })
        rv!!.setAdapter(mAdapt)
        rv!!.setItemAnimator(DefaultItemAnimator())
    }
}
