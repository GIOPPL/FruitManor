package com.gioppl.fruitmanor.view.fragment

import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.gioppl.fruitmanor.R
import com.gioppl.fruitmanor.broadcast.MainBroadcastReceiver
import com.gioppl.fruitmanor.broadcast.MainBroadcastReceiver.BroadCastClassify

abstract class BaseFragment : Fragment(),MainBroadcastReceiver.NetChangeListener {
    private var mainBroadcastReceiver: MainBroadcastReceiver? = null
    private var filter: IntentFilter? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_my, container, false)


    }
    override fun onStart() {
        super.onStart()
        mainBroadcastReceiver = MainBroadcastReceiver()
        mainBroadcastReceiver!!.setListener(this)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) { //Android 7.0以上需要动态注册
            filter = IntentFilter()
            filter!!.addAction(MainBroadcastReceiver.BROADCAST_ACTION_NET_STATUS)
            filter!!.addAction(MainBroadcastReceiver.BROADCAST_ACTION_LOGIN_STATUS)
            activity!!.registerReceiver(mainBroadcastReceiver, filter)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        activity!!.unregisterReceiver(mainBroadcastReceiver)
    }

    override fun onChangeListener(broadCastClassify: MainBroadcastReceiver.BroadCastClassify, statusCode: Int, msg: Any?) {
        receiveBroadCast(broadCastClassify,statusCode,msg)

    }
    abstract fun receiveBroadCast(broadCastClassify: BroadCastClassify, statusCode: Int, msg: Any?)

}