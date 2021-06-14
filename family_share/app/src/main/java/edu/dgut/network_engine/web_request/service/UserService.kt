package edu.dgut.network_engine.web_request.service

import edu.dgut.network_engine.web_request.BaseResponse
import edu.dgut.network_engine.web_request.tdo.Token
import retrofit2.Call
import retrofit2.http.*

interface UserService {

    @POST("user/login")
    suspend fun login(
        @Body map: Map<String, String>,
    ): BaseResponse<Token>

    @GET("get")
    fun get(
        @Query("username") username: String,
        @Query("password") pwd: String
    ): Call<BaseResponse<String>>

}