package com.gioppl.fruitmanor.view.activity
import android.os.Bundle
import android.widget.EditText
import com.gioppl.fruitmanor.R
import com.gioppl.fruitmanor.view.activity.BaseActivity

class RegisterActivity :BaseActivity(){
    private var ed_name: EditText?=null
    private var ed_address: EditText?=null
    private var ed_mail: EditText?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)
        initView()
    }

    private fun initView() {

    }

}