package edu.dgut.network_engine

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity

import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import edu.dgut.network_engine.fragment.WalletFragment


class MainActivity : AppCompatActivity() {

    lateinit var bottomNavigationView: BottomNavigationView
    lateinit var navHostFragment: NavHostFragment
    lateinit var navController: NavController
    lateinit var configuration: AppBarConfiguration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var sharedPreferences: SharedPreferences =
            MyApplication.getContext()!!.getSharedPreferences("data", Context.MODE_PRIVATE)
        var token = sharedPreferences.getString("token", "")
        if (token.isNullOrEmpty()) {
            var intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }


        //底部导航栏绑定
        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment
        navController = navHostFragment.navController
        configuration = AppBarConfiguration.Builder(bottomNavigationView.menu).build()
        NavigationUI.setupActionBarWithNavController(this, navController, configuration)
        NavigationUI.setupWithNavController(bottomNavigationView, navController)

    }

    override fun onResume() {
        super.onResume()
        var sharedPreferences: SharedPreferences =
            MyApplication.getContext()!!.getSharedPreferences("data", Context.MODE_PRIVATE)
        var token = sharedPreferences.getString("token", "")
        if (token.isNullOrEmpty()) {
            var intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

}