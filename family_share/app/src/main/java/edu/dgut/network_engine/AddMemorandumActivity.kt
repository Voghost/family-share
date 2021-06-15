package edu.dgut.network_engine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class AddMemorandumActivity : AppCompatActivity() {
    private lateinit var save:Button
    private lateinit var reset:Button
    private lateinit var edit:EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_memorandum)
        title="添加备忘录"
        save=findViewById(R.id.btn_save)
        reset=findViewById(R.id.btn_reset)
        edit=findViewById(R.id.edit)
        //清空
        reset.setOnClickListener{
            edit.setText("")

        }
        //保存
        save.setOnClickListener{
            finish()
        }
    }
}