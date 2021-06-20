package edu.dgut.network_engine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import edu.dgut.network_engine.view_model.UserViewModel
import kotlinx.android.synthetic.main.activity_modify_info.*
import kotlinx.coroutines.launch

class ModifyInfoActivity : AppCompatActivity() {
    lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modify_info)
        title = "修改个人信息"
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        modify.setOnClickListener {
            var nicknameEdit: EditText = findViewById(R.id.NicknameEdit)
            var valEdit: EditText = findViewById(R.id.PhoneEdit)
            lifecycleScope.launch {
                userViewModel.changInfo(nicknameEdit.text.toString(), valEdit.text.toString())
                finish()
            }
        }
        cancel.setOnClickListener {
            finish()
        }
    }

}