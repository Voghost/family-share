package edu.dgut.network_engine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_detail_member.*

class memberDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_member)
        title = "这是一个家庭成员个人账单详情"

        var bundle = this.intent.extras
        textView2.text = "你已成功跳转到账单详情,当前界面用户Id:" + bundle?.get("userId").toString()

    }
}