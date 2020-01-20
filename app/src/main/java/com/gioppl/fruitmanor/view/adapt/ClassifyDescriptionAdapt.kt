package com.gioppl.fruitmanor.view.adapt

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.facebook.drawee.view.SimpleDraweeView
import com.gioppl.fruitmanor.R
import com.gioppl.fruitmanor.bean.HomeFruitBean


class ClassifyDescriptionAdapt(private var mList:ArrayList<HomeFruitBean>?, private var context: Context):RecyclerView.Adapter<ClassifyDescriptionAdapt.MyFruitViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            = MyFruitViewHolder(LayoutInflater.from(context).inflate(R.layout.classify_description_rv_item,parent,false))

    override fun getItemCount()=if (mList==null) 0 else mList!!.size

    override fun onBindViewHolder(holder: MyFruitViewHolder, position: Int) {
        holder.tv_title!!.text=mList!![position].title
        holder.tv_subtitle!!.text=mList!![position].subtitle
        holder.tv_price!!.text="￥${mList!![position].price}"


        val uri = Uri.parse(mList!![position].imageUrl)
        holder.sim_cherry!!.setImageURI(uri)
    }

    class MyFruitViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var tv_title: TextView? = null
        var tv_subtitle:TextView? = null
        var sim_cherry: SimpleDraweeView? = null
        var tv_price: TextView? = null
        var im_add:ImageView? = null
        init {
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_subtitle = itemView.findViewById(R.id.tv_subtitle)
            tv_price = itemView.findViewById(R.id.tv_price)
            im_add = itemView.findViewById(R.id.im_add)
            sim_cherry = itemView.findViewById(R.id.sim_cherry)
        }
    }

    public fun refreshData(mList:ArrayList<HomeFruitBean>){
        this.mList!!.clear();
        this.mList!!.addAll(mList);
    }
}