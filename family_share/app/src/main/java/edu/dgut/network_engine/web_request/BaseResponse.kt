package edu.dgut.network_engine.web_request

// 基本类型
class BaseResponse<T> {
    var code: Int? = null

    var data: T? = null
    var message: String? = null
    var ok: Boolean? = null

    override fun toString(): String {
        return "BaseResponse(code=$code, data=$data, message=$message, ok=$ok)"
    }

}