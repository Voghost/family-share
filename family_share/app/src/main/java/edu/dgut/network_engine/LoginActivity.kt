package edu.dgut.network_engine

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import edu.dgut.network_engine.database.entity.User
import edu.dgut.network_engine.view_model.UserViewModel
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private lateinit var userViewModel: UserViewModel;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        title = "登录"

        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)


        LoginButton.setOnClickListener {
            var userName: String = UserNameEdit.text.toString()
            var password: String = PassWordEdit.text.toString()
            lifecycleScope.launch {
                var temp: Boolean = userViewModel.login(userName, password)
                if (temp) {
                    var intent = Intent(applicationContext, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }

        RegisterButton.setOnClickListener {
            lifecycleScope.async {
                var user: User? = userViewModel.getMe()
                if (user != null) {
                    Toast.makeText(applicationContext, "本机已存在用户，无需注册", Toast.LENGTH_SHORT).show()
                } else {
                    userViewModel.getAllUserList()
                    var intent = Intent(applicationContext, RegisterActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }

        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val myIntent = Intent(Intent.ACTION_MAIN)
        myIntent.addCategory(Intent.CATEGORY_HOME)
        startActivity(myIntent)
        finish()
    }
}