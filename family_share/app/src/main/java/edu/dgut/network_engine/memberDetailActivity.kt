package edu.dgut.network_engine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.dgut.network_engine.database.adapter.DetailAdapter
import edu.dgut.network_engine.database.entity.UserWithAccountList
import edu.dgut.network_engine.view_model.UserViewModel
import kotlinx.android.synthetic.main.activity_detail_member.*

class memberDetailActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var userViewModel: UserViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_member)
        title = "这是一个家庭成员个人账单详情"

        var bundle = this.intent.extras
        textView2.text = "你已成功跳转到账单详情,当前界面用户Id:" + bundle?.get("userId").toString()

        recyclerView = findViewById(R.id.recyclerView)

        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        val temp =
            userViewModel.getUserWithAccountListByUserId(bundle?.get("userId") as Long)?.observe(this,{ userList: UserWithAccountList ->
                recyclerView.adapter=DetailAdapter(userList,userViewModel)
                recyclerView.layoutManager = LinearLayoutManager(this)
            })

    }
}