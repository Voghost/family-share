package edu.dgut.network_engine

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class addMemberActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_member)
        title = "添加成员"
    }
}