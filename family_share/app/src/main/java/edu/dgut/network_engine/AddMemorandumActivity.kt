package edu.dgut.network_engine

import android.icu.text.SimpleDateFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_add_memorandum.*
import java.util.*

class AddMemorandumActivity : AppCompatActivity() {
    private lateinit var save:Button
    private lateinit var reset:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_memorandum)
        title = "添加备忘录"
        save = findViewById(R.id.btn_save)
        reset = findViewById(R.id.btn_reset)
        //清空
        var memorandumMessage = edit.text.toString()
        reset.setOnClickListener {
            edit.setText("")
        }
        //保存
        save.setOnClickListener {
            Log.v("测试内容",memorandumMessage)
            finish()
        }
    }

}