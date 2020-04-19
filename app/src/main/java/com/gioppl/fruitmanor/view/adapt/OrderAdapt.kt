package com.gioppl.fruitmanor.view.adapt

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.facebook.drawee.view.SimpleDraweeView
import com.gioppl.fruitmanor.R
import com.gioppl.fruitmanor.bean.OrderFormBean

//订单的适配器
class OrderAdapt(private var mList:ArrayList<OrderFormBean>?, private var context: Context, private var couponClickCallBack:  OrderClickCallBack)
    :RecyclerView.Adapter<OrderAdapt.MyOrderHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            = MyOrderHolder(LayoutInflater.from(context).inflate(R.layout.item_order_rv,parent,false))

    override fun getItemCount()=if (mList==null) 0 else mList!!.size

    override fun onBindViewHolder(holder: MyOrderHolder, position: Int) {
        val bean=mList!![position].serverData
        holder.tv_title!!.text=bean.title
        holder.tv_money!!.text="￥${bean.price}"
        holder.sim_cherry!!.setImageURI(Uri.parse(bean.imageUrl))

    }

    class MyOrderHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var tv_title: TextView? = null
        var sim_cherry: SimpleDraweeView? = null
        var tv_money: TextView? = null
        init {
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_money = itemView.findViewById(R.id.tv_money)
            sim_cherry = itemView.findViewById<SimpleDraweeView>(R.id.sim_cherry)
        }
    }

    public fun refreshData(mList:ArrayList<OrderFormBean>){
        this.mList!!.clear();
        this.mList!!.addAll(mList);
    }

    public interface OrderClickCallBack{
        fun lookDescription(position:Int)
    }
}