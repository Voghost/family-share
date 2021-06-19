package edu.dgut.network_engine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_modify_password.*
import kotlinx.android.synthetic.main.person_fragment.*

class ModifyPasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modify_password)
        title="修改密码"
        modify2.setOnClickListener {

        }
        cancel2.setOnClickListener {
            finish()
        }
    }
}