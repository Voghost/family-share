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

        var content = getNow()
        calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            if ((month + 1) / 10 < 1) {
                if (dayOfMonth / 10 < 1) {
                    content = year.toString() + "-0" + (month + 1) + "-0" + dayOfMonth
                } else {
                    content = year.toString() + "-0" + (month + 1) + "-" + dayOfMonth
                }
            } else {
                if (dayOfMonth / 10 < 1) {
                    content = year.toString() + "-" + (month + 1) + "-0" + dayOfMonth
                } else {
                    content = year.toString() + "-" + (month + 1) + "-" + dayOfMonth
                }
            }

            var memorandumMessage = edit.text.toString()

            reset.setOnClickListener {
                edit.setText("")

            }
            //保存
            save.setOnClickListener {
                Log.v("测试内容",memorandumMessage)
                Log.v("测试时间",content)
                finish()
            }
        }
    }

    fun getNow(): String {
        if (android.os.Build.VERSION.SDK_INT >= 24) {
            return SimpleDateFormat("yyyy-MM-dd").format(Date())
        } else {
            var tms = Calendar.getInstance()
            return tms.get(Calendar.YEAR).toString() + "-" + tms.get(Calendar.MONTH)
                .toString() + "-" + tms.get(Calendar.DAY_OF_MONTH).toString() + " " + tms.get(
                Calendar.HOUR_OF_DAY
            ).toString()
        }

    }
}