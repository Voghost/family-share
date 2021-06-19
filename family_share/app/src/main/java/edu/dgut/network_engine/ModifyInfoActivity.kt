package edu.dgut.network_engine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_modify_info.*

class ModifyInfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modify_info)
        title="修改个人信息"

        modify.setOnClickListener{

        }
        cancel.setOnClickListener {
            finish()
        }
    }

}