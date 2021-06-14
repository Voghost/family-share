package edu.dgut.network_engine.web_request.api

import edu.dgut.network_engine.web_request.service.UserService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object UserApi {
    private val api by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.123.45:8020/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder().build())
            .build()
        retrofit.create(UserService::class.java)
    }

    fun get(): UserService {
        return api
    }
}