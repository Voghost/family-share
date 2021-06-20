package edu.dgut.network_engine

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity


class SplashActivity : AppCompatActivity() {
    private val sleepTime=2000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        if (Build.VERSION.SDK_INT >= 21) {
            val decorView = window.decorView
            //让应用主题内容占用系统状态栏的空间,注意:下面两个参数必须一起使用 stable 牢固的
            val option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            decorView.systemUiVisibility = option
            //设置状态栏颜色为透明
            window.statusBarColor = Color.TRANSPARENT
        }
        var actionBar: ActionBar? =supportActionBar
        actionBar?.hide()
    }

    override fun onStart() {
        super.onStart()
        object :Thread(){
            override fun run() {
                var start:Long=System.currentTimeMillis()
                var costTime:Long=System.currentTimeMillis()-start
                if(sleepTime-costTime>0){
                    try{
                        Thread.sleep(sleepTime-costTime)
                    }catch (e:Exception){
                        e.printStackTrace()
                    }
                }
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                finish()
            }
        }.start()
    }
}