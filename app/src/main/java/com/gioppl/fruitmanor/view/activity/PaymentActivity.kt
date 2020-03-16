package com.gioppl.fruitmanor.view.activity

import android.os.Bundle
import android.view.View
import com.gioppl.fruitmanor.broadcast.MainBroadcastReceiver.BroadCastClassify
import com.gioppl.fruitmanor.databinding.ActivityPaymentBinding

class PaymentActivity : BaseActivity() {
    private var root: View?=null
    public override fun receiveBroadCast(broadCastClassify: BroadCastClassify, statusCode: Int, msg: Any?) {}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mBinding= ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(mBinding!!.root)
    }

    fun back(view: View?) {
        finish()
    }
}