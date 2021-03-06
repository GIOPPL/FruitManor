package com.gioppl.fruitmanor.view.adapt

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.facebook.drawee.view.SimpleDraweeView
import com.gioppl.fruitmanor.R
import com.gioppl.fruitmanor.bean.CouponBean


class CouponAdapt(private var mList:ArrayList<CouponBean>?, private var context: Context, private var couponClickCallBack:  CouponClickCallBack)
    :RecyclerView.Adapter<CouponAdapt.MyCouponHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            = MyCouponHolder(LayoutInflater.from(context).inflate(R.layout.item_coupon_rv,parent,false))

    override fun getItemCount()=if (mList==null) 0 else mList!!.size

    override fun onBindViewHolder(holder: MyCouponHolder, position: Int) {
        val bean=mList!![position]
        holder.tv_title!!.text=bean.title
        holder.tv_money!!.text="￥${bean.reduce_money}"
        val uri = Uri.parse(bean.imageUrl)
        holder.sim_cherry!!.setImageURI(uri)
        holder.tv_endTime!!.text="截止日期："+bean.endTime
        holder.ll_coupon!!.setOnClickListener {
            couponClickCallBack.lookDescription(position)
        }
    }

    class MyCouponHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var tv_title: TextView? = null
        var sim_cherry: SimpleDraweeView? = null
        var tv_money: TextView? = null
        var tv_endTime: TextView? = null
        var ll_coupon:LinearLayout?=null
        init {
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_money = itemView.findViewById(R.id.tv_money)
            tv_endTime = itemView.findViewById(R.id.tv_endTime)
            ll_coupon = itemView.findViewById(R.id.ll_coupon)
            sim_cherry = itemView.findViewById(R.id.sim_cherry)
        }
    }

    public fun refreshData(mList:ArrayList<CouponBean>){
        this.mList!!.clear();
        this.mList!!.addAll(mList);
    }

    public interface CouponClickCallBack{
        fun lookDescription(position:Int)
    }
}