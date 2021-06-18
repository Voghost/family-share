package edu.dgut.network_engine

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import edu.dgut.network_engine.database.entity.User
import edu.dgut.network_engine.view_model.UserViewModel
import edu.dgut.network_engine.web_request.tdo.NewUserTdo
import kotlinx.android.synthetic.main.activity_add_member.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.PassWordEdit
import kotlinx.android.synthetic.main.activity_login.UserNameEdit
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.coroutines.launch
import java.util.*
import kotlin.properties.Delegates

class RegisterActivity : AppCompatActivity() {
    private lateinit var userViewModel: UserViewModel;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        title = "注册"

        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        Register1Button.setOnClickListener {
            var userName = UserName1Edit.text.toString()
            var password = PassWord1Edit.text.toString()
            var repassword = PassWordAgainEdit.text.toString()
            var phone = PhoneEdit.text.toString()

            if (password != repassword) {
                var builder: AlertDialog.Builder = AlertDialog.Builder(this)
                builder.setMessage("两次密码输入不同")
                builder.setTitle("提示")
                builder.setPositiveButton(
                    "确定"
                ) { dialog, which ->
                }
            }
            lifecycleScope.launch {
                var newUserTdo: NewUserTdo = NewUserTdo()
                newUserTdo.isMe = true
                newUserTdo.createTime = Date().time
                newUserTdo.username = userName
                newUserTdo.password = password
                if (userViewModel.register(newUserTdo)) {
                    var intent = Intent(applicationContext, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
            //注册成功弹窗提示注册成功并返回登录界面
        }

        BackLoginButton.setOnClickListener {
            var intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}