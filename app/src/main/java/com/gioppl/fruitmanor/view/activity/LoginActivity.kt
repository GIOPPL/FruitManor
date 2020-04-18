package com.gioppl.fruitmanor.view.activity

import android.app.ActionBar
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import com.avos.avoscloud.AVException
import com.avos.avoscloud.AVUser
import com.avos.avoscloud.LogInCallback
import com.gioppl.fruitmanor.R
import com.gioppl.fruitmanor.broadcast.MainBroadcastReceiver
import com.gioppl.fruitmanor.tool.LoginStatusChangedTools
import com.gioppl.fruitmanor.tool.SharedPreferencesUtils


class LoginActivity : BaseActivity() {
    var loginIntentFilter:IntentFilter?=null
    var ed_user: EditText? = null
    var ed_psw: EditText? = null
    var ll_home:LinearLayout?=null
    override fun receiveBroadCast(broadCastClassify: MainBroadcastReceiver.BroadCastClassify?, statusCode: Int, msg: Any?) {

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initView()
        initData()
        ll_home=findViewById(R.id.ll_home)
        val imageView = ImageView(this)
        imageView.layoutParams = ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT) //设置图片宽高
        imageView.setImageResource(R.mipmap.ic_kiwi) //图片资源
        imageView.translationZ=5F
        ll_home!!.addView(imageView) //动态添加图片
    }

    private fun initData() {


    }

    private fun initView() {
        ed_psw = findViewById(R.id.ed_login_psw)
        ed_user = findViewById(R.id.ed_login_user)
    }

    public fun login(view: View) {
        val user=ed_user!!.text.toString()
        val password=ed_psw!!.text.toString()

        if (user==""||password=="")
            mango(this,"请输入正确的账户或者密码")
        AVUser.logInInBackground(user, password, object : LogInCallback<AVUser>() {
            override fun done(user: AVUser?, e: AVException?) {
                if (e==null){
                    SharedPreferencesUtils.getInstance().saveData("nickName",user!!.get("nickName")as String)
                    SharedPreferencesUtils.getInstance().saveData("imageUrl",user.get("imageUrl")as String)
                    SharedPreferencesUtils.getInstance().saveData("objectId",user.objectId)
                    SharedPreferencesUtils.getInstance().saveData("phoneNumber",user.username)
                    SharedPreferencesUtils.getInstance().saveData("loginStatus",true)
                    SharedPreferencesUtils.getInstance().saveData("money",user.get("money")as Int)

                    LoginStatusChangedTools(this@LoginActivity)
                    finish()
//                    val intent=Intent();
//                    intent.action= BROADCAST_ACTION_LOGIN_STATUS
//                    intent.putExtra("LOGIN_STATUS",true)
//                    sendBroadcast(intent);
                }else{
                    strawberry(this,"登陆失败:${e.code}- -${e.message}")
                    mango(this@LoginActivity,"登陆失败:${e.code}- -${e.message}")
                }
            }
        })
    }
    public fun register(view: View) {
        startActivity(Intent(this@LoginActivity,RegisterActivity::class.java))
    }

    public fun back(view: View) {
        finish()
    }




}