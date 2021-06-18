package edu.dgut.network_engine

import android.content.Intent
import android.icu.text.SimpleDateFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import edu.dgut.network_engine.database.adapter.MemberAdapter
import edu.dgut.network_engine.database.entity.Memorandum
import edu.dgut.network_engine.database.entity.User
import edu.dgut.network_engine.database.entity.UserWithAccountList
import edu.dgut.network_engine.fragment.MemorandumFragment
import edu.dgut.network_engine.view_model.MemorandumViewModel
import edu.dgut.network_engine.view_model.UserViewModel
import kotlinx.android.synthetic.main.activity_add_memorandum.*
import kotlinx.coroutines.async
import java.util.*

class AddMemorandumActivity : AppCompatActivity() {
    private lateinit var save: Button
    private lateinit var reset: Button
    private lateinit var userViewModel: UserViewModel
    private lateinit var memorandumViewModel: MemorandumViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_memorandum)

        var flag = 0
        var bundle = this.intent.extras
        var content = bundle?.get("Content")
        if (content != null) {
            flag = 1
            title = "修改备忘录"
            var introduceTemp = SpannableStringBuilder(content.toString())
            edit.text = introduceTemp
        } else {
            title = "添加备忘录"
        }

        save = findViewById(R.id.btn_save)
        reset = findViewById(R.id.btn_reset)
        //清空
        reset.setOnClickListener {
            edit.setText("")
        }
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        memorandumViewModel = ViewModelProvider(this).get(MemorandumViewModel::class.java)
        //保存
        save.setOnClickListener {
            var createtime = Date()
            lifecycleScope.async {
                var user: User? = userViewModel.getMe()
                var userid = user?.userId
                var memorandumMessage = edit.text.toString()
                var temp = Memorandum(
                    null, memorandumMessage, createtime, null, null, null, userid
                )
                if (flag == 1) {
                    Log.v("执行了", "更新操作")
                    memorandumViewModel.updateMemorandum(temp)
                } else {
                    Log.v("执行了", "添加操作")
                    memorandumViewModel.insertMemorandum(temp)
                }
                finish()
            }

        }
    }

}