package edu.dgut.network_engine.web_request.service

import edu.dgut.network_engine.database.entity.Account
import edu.dgut.network_engine.database.entity.Memorandum
import edu.dgut.network_engine.web_request.BaseResponse
import edu.dgut.network_engine.web_request.tdo.AccountTdo
import retrofit2.http.Body
import retrofit2.http.HTTP
import retrofit2.http.POST

interface AccountService {

    /**
     * 同步
     */
    @POST("account/syn")
    suspend fun syn(@Body accountList: List<AccountTdo>): BaseResponse<List<AccountTdo>>

    /**
     * 删除
     */
    @HTTP(method = "DELETE", path = "account/delete", hasBody = true)
    suspend fun delete(@Body id: Long): BaseResponse<String>


}