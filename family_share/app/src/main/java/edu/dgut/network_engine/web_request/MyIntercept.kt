package edu.dgut.network_engine.web_request

import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import android.app.Application
import edu.dgut.network_engine.MyApplication


class MyIntercept : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var original = chain.request()

        val sharedPreferences: SharedPreferences =
            MyApplication.getContext()!!.getSharedPreferences("data", Context.MODE_PRIVATE)
        var token = sharedPreferences.getString("token", "")

        var request: Request = original.newBuilder()
            .addHeader("token", token!!)
            .build()

        return chain.proceed(request)
    }
}