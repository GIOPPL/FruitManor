package com.gioppl.fruitmanor.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import com.avos.avoscloud.AVException
import com.avos.avoscloud.AVUser
import com.avos.avoscloud.LogInCallback
import com.gioppl.fruitmanor.R
import com.gioppl.fruitmanor.tool.SharedPreferencesUtils

class LoginActivity : BaseActivity() {

    var ed_user: EditText? = null
    var ed_psw: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)
        initView()


    }

    private fun initView() {
        ed_psw = findViewById(R.id.ed_login_psw)
        ed_user = findViewById(R.id.ed_login_user)
    }

    public fun login(view: View) {
        val user=ed_user!!.text.toString()
        val password=ed_psw!!.text.toString()

        if (user==""||password=="")
            mogo(this,"请输入正确的账户或者密码")
        AVUser.logInInBackground(user, password, object : LogInCallback<AVUser>() {
            override fun done(user: AVUser?, e: AVException?) {
                if (e==null){
                    SharedPreferencesUtils.getInstance().saveData("nickName",user!!.get("nickName")as String)
                    SharedPreferencesUtils.getInstance().saveData("imageUrl",user.get("imageUrl")as String)
                    SharedPreferencesUtils.getInstance().saveData("objectId",user.objectId)
                    SharedPreferencesUtils.getInstance().saveData("phoneNumber",user.username)
                    startActivity(Intent(this@LoginActivity,MainActivity::class.java))
                    finish()
                }else{
                    strawberry(this,"登陆失败:${e.code}- -${e.message}")
                    mogo(this@LoginActivity,"登陆失败:${e.code}- -${e.message}")
                }
            }
        })
    }
    public fun register(view: View) {

    }

    public fun back(view: View) {
        finish()
    }

}