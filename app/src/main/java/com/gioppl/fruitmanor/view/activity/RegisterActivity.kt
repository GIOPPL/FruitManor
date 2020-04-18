package com.gioppl.fruitmanor.view.activity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import com.avos.avoscloud.AVException
import com.avos.avoscloud.AVUser
import com.avos.avoscloud.SignUpCallback
import com.gioppl.fruitmanor.R
import com.gioppl.fruitmanor.broadcast.MainBroadcastReceiver

class RegisterActivity :BaseActivity(){
    var ed_user: EditText? = null
    var ed_psw: EditText? = null
    var ll_home: LinearLayout?=null
    override fun receiveBroadCast(broadCastClassify: MainBroadcastReceiver.BroadCastClassify?, statusCode: Int, msg: Any?) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)
        initView()
    }

    private fun initView() {
        ed_psw = findViewById(R.id.ed_login_psw)
        ed_user = findViewById(R.id.ed_login_user)
    }

    fun startRegister(view: View) {
        val userName=ed_user!!.text.toString()
        val userPassword=ed_psw!!.text.toString()
        if (userName==""||userPassword==""){
            mango(this@RegisterActivity,"用户名或者密码为空")
            return
        }
        val user = AVUser()// 新建 AVUser 对象实例
        user.username = userName// 设置用户名
        user.mobilePhoneNumber=userName
        user.setPassword(userPassword)// 设置密码
        user.signUpInBackground(object : SignUpCallback() {
            override fun done(e: AVException?) {
                if (e == null) {
                    // 注册成功
                    mango(this@RegisterActivity, "注册成功")
                    finish()
                } else {
                    mango(this@RegisterActivity, "注册失败,错误信息：${e.message}")
                    // 失败的原因可能有多种，常见的是用户名已经存在。
                }
            }
        })
    }
    fun back(view: View) {
        finish()
    }


}