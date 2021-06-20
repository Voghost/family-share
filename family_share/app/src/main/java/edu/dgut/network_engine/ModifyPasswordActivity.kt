package edu.dgut.network_engine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import edu.dgut.network_engine.view_model.UserViewModel
import kotlinx.android.synthetic.main.activity_modify_password.*
import kotlinx.android.synthetic.main.person_fragment.*
import kotlinx.coroutines.launch

class ModifyPasswordActivity : AppCompatActivity() {
    lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modify_password)
        title = "修改密码"
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        modify2.setOnClickListener {
            if (newPassword.text.toString() == newPassword2.text.toString()) {
                lifecycleScope.launch {
                    userViewModel.changePass(
                        oldPassword.text.toString(),
                        newPassword.text.toString()
                    )
                    finish()
                }

            } else {
                Toast.makeText(this.applicationContext, "两次密码不一样", Toast.LENGTH_SHORT).show()
            }


        }
        cancel2.setOnClickListener {
            finish()
        }
    }
}