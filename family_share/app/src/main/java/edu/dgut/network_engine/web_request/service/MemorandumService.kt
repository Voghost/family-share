package edu.dgut.network_engine.web_request.service

import edu.dgut.network_engine.database.entity.Memorandum
import edu.dgut.network_engine.web_request.BaseResponse
import edu.dgut.network_engine.web_request.tdo.TokenTdo
import okhttp3.internal.http.hasBody
import retrofit2.http.*

interface MemorandumService {
    @GET("memorandum/all")
    suspend fun all(): BaseResponse<List<Memorandum>>

    @POST("memorandum/insert")
    suspend fun insert(@Body memorandum: Memorandum): BaseResponse<Memorandum>

    @PUT("memorandum/update")
    suspend fun update(@Body memorandum: Memorandum): BaseResponse<Memorandum>

    @HTTP(method = "DELETE", path = "memorandum/delete", hasBody = true)
    suspend fun delete(@Body id: Long): BaseResponse<Memorandum>
}