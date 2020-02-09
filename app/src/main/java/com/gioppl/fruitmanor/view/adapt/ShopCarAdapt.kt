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


class ShopCarAdapt(private var mList: ArrayList<HomeFruitBean>?, private var context: Context, var selectCallBack: SelectCallBack) : RecyclerView.Adapter<ShopCarAdapt.MyFruitViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            = MyFruitViewHolder(LayoutInflater.from(context).inflate(R.layout.shop_car_rv_item, parent, false))

    override fun getItemCount() = if (mList == null) 0 else mList!!.size

    override fun onBindViewHolder(holder: MyFruitViewHolder, position: Int) {
        val bean =mList!![position]
        holder.tv_title!!.text = bean.title
        holder.tv_price!!.text = "￥${bean.price}"
        val uri = Uri.parse(bean.imageUrl)
        holder.sim_cherry!!.setImageURI(uri)
        if (bean.isSelect) {
            holder.im_select!!.setImageDrawable(context.getDrawable(R.mipmap.select_pressed))
        } else {
            holder.im_select!!.setImageDrawable(context.getDrawable(R.mipmap.select_normal))
        }
        holder.im_select!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                selectCallBack.back(position,bean.price,!mList!![position].isSelect)
                mList!![position].isSelect=!mList!![position].isSelect
                notifyItemChanged(position)
            }
        })
    }

    class MyFruitViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tv_title: TextView? = null
        var sim_cherry: SimpleDraweeView? = null
        var tv_price: TextView? = null
        var tv_find_like: TextView? = null
        var im_select: ImageView? = null

        init {
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_price = itemView.findViewById(R.id.tv_price)
            sim_cherry = itemView.findViewById(R.id.sim_cherry)
            tv_find_like = itemView.findViewById(R.id.tv_find_like)
            im_select = itemView.findViewById(R.id.im_select)
        }
    }

    public interface SelectCallBack {
        fun back(position: Int, price: Float,isAdd:Boolean)
    }

    public fun refreshData(mList: ArrayList<HomeFruitBean>) {
        this.mList!!.clear();
        this.mList!!.addAll(mList);
    }
}