package edu.dgut.network_engine.web_request.api

import com.google.gson.GsonBuilder
import edu.dgut.network_engine.web_request.MyIntercept
import edu.dgut.network_engine.web_request.service.MemorandumService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object MemorandumApi {
    private val gsonFormat = GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create()

    private val api by lazy {
        val retrofit = Retrofit.Builder()
//            .baseUrl("http://81.71.17.83/")
//            .baseUrl("http://192.168.123.45:8080/")
//            .baseUrl("http://10.62.98.193:8080/")
            .baseUrl("https://api-android.ghovos.top/")

//            .baseUrl("http://192.168.1.122:8080/")
            .addConverterFactory(GsonConverterFactory.create(gsonFormat))
            .client(
                OkHttpClient
                    .Builder()
                    .addInterceptor(MyIntercept())
                    .build()
            )
            .build()
        retrofit.create(MemorandumService::class.java)
    }

    fun get(): MemorandumService{
        return api
    }
}
