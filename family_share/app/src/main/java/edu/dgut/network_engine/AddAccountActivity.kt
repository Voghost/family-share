package edu.dgut.network_engine

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.CheckBox
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import edu.dgut.network_engine.database.entity.Account
import edu.dgut.network_engine.view_model.AccountViewModel
import edu.dgut.network_engine.view_model.UserViewModel
import edu.dgut.network_engine.view_model.WalletViewModel
import kotlinx.android.synthetic.main.activity_add_account.*
import java.math.BigDecimal
import kotlin.properties.Delegates

class AddAccountActivity : AppCompatActivity() {

    private lateinit var walletViewModel: WalletViewModel
    private var accountId by Delegates.notNull<Long>()
    private var userId by Delegates.notNull<Long>()
    private lateinit var username : String

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_account)

        var bundle = this.intent.extras
        var userid = bundle?.get("userId")
        Log.v("测试",userid.toString())

        title = "添加账单"
        //checkbox单选
        var checkBoxA:CheckBox=findViewById(R.id.checkBoxA)
        var checkBoxB:CheckBox=findViewById(R.id.checkBoxB)
        //type为1时类型为支出，2为收入
        var type = 1
        checkBoxA.setOnCheckedChangeListener { buttonView, isChecked ->
           if(isChecked) {
               checkBoxB.isChecked=false
               type = 1
               //数据处理
           }
        }
        checkBoxB.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked) {
                checkBoxA.isChecked=false
                type = 2
                //数据处理
            }
        }

        button1.setOnClickListener {
            var price = inputprice.text.toString().toBigDecimal()
            if(type == 2){
                price = price.multiply(BigDecimal(-1))
            }
            var introduce = inputintroduce.text.toString()
            walletViewModel = ViewModelProvider(this).get(WalletViewModel::class.java)
            Log.v("测试测试测试",price.toString())
            var account = Account(
                null,1,price,System.currentTimeMillis(),System.currentTimeMillis(), userid as Long,introduce,false
            )
            walletViewModel.insertAccount(account)
            finish()
        }
    }
}