package edu.dgut.network_engine

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import edu.dgut.network_engine.database.entity.User
import edu.dgut.network_engine.view_model.UserViewModel
import kotlinx.android.synthetic.main.activity_add_member.*
import kotlinx.android.synthetic.main.wallet_fragment.*
import kotlin.properties.Delegates

class addMemberActivity : AppCompatActivity() {

    private lateinit var userViewModel: UserViewModel
    private var userid by Delegates.notNull<Long>()
    private lateinit var username : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_member)
        title = "添加成员"

        button.setOnClickListener {
            userid = textView4.text.toString().toLong()

            username = textView3.text.toString()

            userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

            var user = User(userid,username,null,null,null,4124124124,51251255,null,false)

            userViewModel.insertUser(user)

            var intent= Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }
}