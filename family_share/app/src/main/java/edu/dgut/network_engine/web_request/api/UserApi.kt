package edu.dgut.network_engine.web_request.api

import android.media.session.MediaSession
import edu.dgut.network_engine.web_request.BaseResponse
import edu.dgut.network_engine.web_request.tdo.Token
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface UserApi {
    @POST("user/login")
    @FormUrlEncoded
    fun post(
        @Field("username") userName: String,
        @Field("password") pwd: String
    ): BaseResponse<Token>

    @GET("get")
    fun get(
        @Query("username") username: String,
        @Query("password") pwd: String
    ): Call<ResponseBody>
}