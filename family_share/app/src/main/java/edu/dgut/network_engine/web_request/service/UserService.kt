package edu.dgut.network_engine.web_request.service

import edu.dgut.network_engine.database.entity.User
import edu.dgut.network_engine.web_request.BaseResponse
import edu.dgut.network_engine.web_request.tdo.Token
import retrofit2.Call
import retrofit2.http.*

interface UserService {

    @POST("user/login")
    suspend fun login(
        @Body map: Map<String, String>,
    ): BaseResponse<Token>

    @POST("user/register")
    suspend fun register(
        @Body user: User
    ): BaseResponse<User>

}