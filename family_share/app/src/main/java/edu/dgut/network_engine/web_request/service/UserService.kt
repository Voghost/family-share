package edu.dgut.network_engine.web_request.service

import edu.dgut.network_engine.database.entity.User
import edu.dgut.network_engine.database.entity.UserWithUserList
import edu.dgut.network_engine.web_request.BaseResponse
import edu.dgut.network_engine.web_request.tdo.NewUserTdo
import edu.dgut.network_engine.web_request.tdo.TokenTdo
import retrofit2.http.*

interface UserService {

    @POST("user/login")
    suspend fun login(
        @Body map: Map<String, String>,
    ): BaseResponse<TokenTdo>

    @POST("user/register")
    suspend fun register(
        @Body newUserTdo: NewUserTdo
    ): BaseResponse<NewUserTdo>

    @GET("user/is_username_exist")
    suspend fun isUsernameExist(@Query("username") username: String): BaseResponse<Boolean>

    @POST("user/join_family")
    @FormUrlEncoded
    suspend fun joinFamily(
        @Field("token") token: String,
        @Field("familyId") id: Long
    ): BaseResponse<List<NewUserTdo>>

    @POST("user/syn_family")
    suspend fun synFamily(@Body userList: List<User>) :BaseResponse<List<NewUserTdo>>

}