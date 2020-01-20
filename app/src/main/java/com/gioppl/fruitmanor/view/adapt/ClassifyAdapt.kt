package com.gioppl.fruitmanor.view.adapt

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gioppl.fruitmanor.R


class ClassifyAdapt(private var mList: ArrayList<ClassifyButtonInfo>, private var context: Context, private val clickBack: ClickBack) : RecyclerView.Adapter<ClassifyAdapt.MyFruitViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MyFruitViewHolder(LayoutInflater.from(context).inflate(R.layout.classify_variety_rv_item, parent, false))

    override fun getItemCount() =  mList.size

    override fun onBindViewHolder(holder: MyFruitViewHolder, position: Int) {
        holder.tv_variety!!.text = mList[position].text;
        holder.tv_variety!!.setTextColor(context.resources.getColor(mList[position].textColor))
        holder.view!!.setBackgroundColor(context.resources.getColor(mList[position].flagColor))
        holder.ll_classify!!.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                clickBack.back(position)
//                holder.view!!.setBackgroundColor(context.resources.getColor(R.color.appMainColor));
            }
        })
    }

    class MyFruitViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tv_variety: TextView? = null
        var ll_classify: LinearLayout? = null
        var view:View?=null

        init {
            tv_variety = itemView.findViewById(R.id.tv_variety);
            ll_classify = itemView.findViewById(R.id.ll_classify)
            view=itemView.findViewById(R.id.view)
        }
    }

    public interface ClickBack {
        fun back(position: Int)
    }

    public class ClassifyButtonInfo(public var classifyNum: Int=0,public var text:String ="火龙果",public var textColor: Int=android.R.color.black,public var flagColor:Int=android.R.color.white)

}