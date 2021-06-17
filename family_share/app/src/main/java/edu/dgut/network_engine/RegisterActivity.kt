package edu.dgut.network_engine

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import edu.dgut.network_engine.database.entity.User
import edu.dgut.network_engine.view_model.UserViewModel
import kotlinx.android.synthetic.main.activity_add_member.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.PassWordEdit
import kotlinx.android.synthetic.main.activity_login.UserNameEdit
import kotlinx.android.synthetic.main.activity_register.*
import kotlin.properties.Delegates

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        title = "注册"


        var userName = UserName1Edit.text.toString()
        var password = PassWord1Edit.text.toString()
        var repassword = PassWordAgainEdit.text.toString()
        var phone = PhoneEdit.text.toString()

        if(password != repassword){
            var builder: AlertDialog.Builder= AlertDialog.Builder(this)
            builder.setMessage("两次密码输入不同")
            builder.setTitle("提示")
            builder.setPositiveButton("确定"
            ) { dialog, which ->
            }
        }

        Register1Button.setOnClickListener {
            //注册成功弹窗提示注册成功并返回登录界面
        }

        BackLoginButton.setOnClickListener {
            var intent = Intent("android.intent.action.LoginActivity")
            startActivity(intent)
        }
    }
}