package edu.dgut.network_engine

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.dgut.network_engine.database.adapter.DetailAdapter
import edu.dgut.network_engine.database.adapter.EmptyAdapter
import edu.dgut.network_engine.database.entity.UserWithAccountList
import edu.dgut.network_engine.view_model.UserViewModel
import kotlinx.android.synthetic.main.activity_detail_member.*

class memberDetailActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var userViewModel: UserViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_member)
        title = "个人账单详情"

        var bundle = this.intent.extras
        textView13.text = "你已成功跳转到账单详情,当前界面用户Id:" + bundle?.get("userId").toString()
        textView14.text = ""


        recyclerView = findViewById(R.id.recyclerView)
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        val temp =
            userViewModel.getUserWithAccountListByUserId(bundle?.get("userId") as Long)
                ?.observe(this, { userList: UserWithAccountList ->
                    if(userList.accountList!!.isEmpty()){
                        Log.v("测试","此用户无账单")
                        textView13.text = "此用户当前无任何账单"
                        recyclerView.adapter = EmptyAdapter(userList,userViewModel)
                    }else{
                        var cost = 0.0
                        var income = 0.0
                        for(i in 0..userList.accountList!!.size-1){
                            if(userList.accountList!![i].price!!.signum() == 1){
                                cost += userList.accountList!![i].price!!.toDouble()
                            }else{
                                income += userList.accountList!![i].price!!.abs().toDouble()
                            }
                        }
                        textView13.text = "支出:" + cost.toString()
                        textView14.text = "收入:" + income.toString()
                        recyclerView.adapter = DetailAdapter(userList, userViewModel)
                    }
                    recyclerView.layoutManager = LinearLayoutManager(this)
                })

        button4.setOnClickListener {
            var intent = Intent("android.intent.action.AddAccountActivity")
            startActivity(intent)
        }

    }
}