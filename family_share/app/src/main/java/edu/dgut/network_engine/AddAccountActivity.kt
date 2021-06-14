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
import edu.dgut.network_engine.view_model.AccountViewModel
import edu.dgut.network_engine.view_model.UserViewModel
import kotlinx.android.synthetic.main.activity_add_account.*
import kotlinx.android.synthetic.main.activity_add_member.*
import kotlinx.android.synthetic.main.wallet_fragment.*
import kotlin.properties.Delegates

class AddAccountActivity : AppCompatActivity() {

    private lateinit var accountViewModel: AccountViewModel
    private var userid by Delegates.notNull<Long>()
    private lateinit var username : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_account)
        title = "添加账单"

    }
}