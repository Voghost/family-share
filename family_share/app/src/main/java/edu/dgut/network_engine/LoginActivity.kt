package edu.dgut.network_engine

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import edu.dgut.network_engine.database.entity.User
import edu.dgut.network_engine.view_model.UserViewModel
import kotlinx.android.synthetic.main.activity_add_member.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlin.properties.Delegates

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        title = "登录"


        var userName = UserNameEdit.text.toString()
        var password = PassWordEdit.text.toString()

        LoginButton.setOnClickListener {

        }

        RegisterButton.setOnClickListener {
            var intent = Intent(this,RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}