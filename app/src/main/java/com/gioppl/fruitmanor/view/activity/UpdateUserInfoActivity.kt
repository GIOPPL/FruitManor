package com.gioppl.fruitmanor.view.activity

import android.os.Bundle
import android.text.InputType
import android.view.View
import com.gioppl.fruitmanor.broadcast.MainBroadcastReceiver.BroadCastClassify
import com.gioppl.fruitmanor.databinding.ActivityUpdateUserInfoBinding
import com.gioppl.fruitmanor.net.UpdateUserInfoCloud
import com.gioppl.fruitmanor.tool.LoginStatusChangedTools
import com.gioppl.fruitmanor.tool.SharedPreferencesUtils
import kotlinx.android.synthetic.main.activity_update_user_info.view.*

class UpdateUserInfoActivity : BaseActivity() {
    private var root: View?=null
    private var mode=0;
    public override fun receiveBroadCast(broadCastClassify: BroadCastClassify, statusCode: Int, msg: Any?) {}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mBinding= ActivityUpdateUserInfoBinding.inflate(layoutInflater)
        root=mBinding!!.root
        setContentView(mBinding.root)
        initDta()
    }
    private fun initDta(){//这里的模式0为修改昵称，1为修改地址，2为修改密码
        mode=intent.getIntExtra("mode",0)
        when(mode){
            0->{
                root!!.tv_title.text="修改昵称"
                root!!.tv_initialName.text="原昵称"
                root!!.et_alter.hint = "请输入修改昵称"
                val initial=SharedPreferencesUtils.getInstance().getData("nickName","（未命名）") as String
                root!!.et_alter_initial.setText(initial)
                root!!.et_alter_initial.isFocusable = false;
                root!!.et_alter_initial.isFocusableInTouchMode = false;
            }
            1->{
                root!!.tv_title.text="修改地址"
                root!!.tv_initialName.text="原地址"
                root!!.et_alter.hint = "请输入修改地址"
                val initial=SharedPreferencesUtils.getInstance().getData("address","（没有地址）") as String
                root!!.et_alter_initial.setText(initial)
                root!!.et_alter_initial.isFocusable = false;
                root!!.et_alter_initial.isFocusableInTouchMode = false;
            }
            2->{
                root!!.et_alter.inputType= InputType.TYPE_TEXT_VARIATION_PASSWORD or InputType.TYPE_CLASS_TEXT
                root!!.et_alter_initial.inputType= InputType.TYPE_TEXT_VARIATION_PASSWORD or InputType.TYPE_CLASS_TEXT
                root!!.tv_title.text="修改密码"
                root!!.tv_initialName.text="原密码"
                root!!.et_alter_initial.hint = "请输入原密码"
                root!!.et_alter.hint = "请输入修改密码"
                root!!.et_alter_initial.isFocusable = true;
                root!!.et_alter_initial.isFocusableInTouchMode = true;
                root!!.et_alter_initial.requestFocus();
            }
        }
    }

    fun back(view: View?) {
        finish()
    }

    //执行修改操作
    fun startAlter(view: View) {
        val s1=root!!.et_alter_initial.text.toString()
        val s2=root!!.et_alter.text.toString()
        val nickname=SharedPreferencesUtils.getInstance().getData("nickName","") as String
        val address=SharedPreferencesUtils.getInstance().getData("address","") as String
        val password=SharedPreferencesUtils.getInstance().getData("password","") as String
        val objectId=SharedPreferencesUtils.getInstance().getData("objectId","") as String
        when(mode){
            0->{//修改昵称
                UpdateUserInfoCloud(this,objectId,s2,address,password)
                SharedPreferencesUtils.getInstance().saveData("nickName",nickname)
                mango(this,"修改昵称成功")
                finish()
            }
            1->{//修改地址
                UpdateUserInfoCloud(this,objectId,nickname,s2,password)
                SharedPreferencesUtils.getInstance().saveData("address",address)
                mango(this,"修改地址成功")
                finish()
            }
            2->{//修改密码
                if (s1==password){
                    UpdateUserInfoCloud(this,objectId,nickname,address,s2)
                    SharedPreferencesUtils.getInstance().saveData("password",password)
                    mango(this,"修改密码成功")
                    finish()
                }else{
                    mango(this,"原密码不正确")
                }
            }
        }
        LoginStatusChangedTools(this)
    }
}