package edu.dgut.network_engine

import android.app.DatePickerDialog
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
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
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class AddMemorandumActivity : AppCompatActivity() {
    private lateinit var save:Button
    private lateinit var reset:Button
    private lateinit var choice:Button
    private lateinit var userViewModel: UserViewModel
    private lateinit var memorandumViewModel: MemorandumViewModel
    @RequiresApi(Build.VERSION_CODES.O)
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
        choice = findViewById(R.id.btn_choice)
        //清空
        var memorandumMessage = edit.text.toString()
        reset.setOnClickListener {
            edit.setText("")
        }
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        memorandumViewModel = ViewModelProvider(this).get(MemorandumViewModel::class.java)
        //保存
        save.setOnClickListener {
            var createtime = Date()
            Log.v("执行了","点击操作")
            lifecycleScope.async {
                var user: User? = userViewModel.getMe()
                var userid = user?.userId
                var memorandumMessage = edit.text.toString()
                var temp = Memorandum()
                var mDate = EndDate.text.toString()
                if(mDate == "截止日期"){
                    temp = Memorandum(
                        null,memorandumMessage,createtime,null,null,null,userid
                    )
                }else{
                    val parsedDate = SimpleDateFormat("yyyy-MM-dd").parse(mDate)
                    temp = Memorandum(
                        null,memorandumMessage,createtime,null,parsedDate,null,userid
                    )
                }

                Log.v("查看新建内容",temp.toString())
                if(flag == 1){
                    Log.v("执行了","更新操作")
                    memorandumViewModel.updateMemorandum(temp)
                } else {
                    Log.v("执行了", "添加操作")
                    memorandumViewModel.insertMemorandum(temp)
                }
                finish()
            }
        }

        //选择截止日期
        choice.setOnClickListener {
            val ca = Calendar.getInstance()
            var mYear = ca[Calendar.YEAR]
            var mMonth = ca[Calendar.MONTH]
            var mDay = ca[Calendar.DAY_OF_MONTH]

            val datePickerDialog = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                    mYear = year
                    mMonth = month
                    mDay = dayOfMonth
//                            val mDate = "${year}/${month + 1}/${dayOfMonth}"
                    // 将选择的日期赋值给TextView
                    var mDate = ""
                    if ((mMonth + 1) / 10 < 1) {
                        if (dayOfMonth / 10 < 1) {
                            mDate = mYear.toString() + "-0" + (mMonth + 1) + "-0" + mDay
                        } else {
                            mDate = mYear.toString() + "-0" + (mMonth + 1) + "-" + mDay
                        }
                    } else {
                        if (mDay / 10 < 1) {
                            mDate = mYear.toString() + "-" + (mMonth + 1) + "-0" + mDay
                        } else {
                            mDate = mYear.toString() + "-" + (mMonth + 1) + "-" + mDay
                        }
                    }
                    EndDate.text = mDate
                },
                mYear, mMonth, mDay
            )
            datePickerDialog.show()
        }
    }

}