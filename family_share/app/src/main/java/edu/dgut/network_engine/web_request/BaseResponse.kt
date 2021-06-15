package edu.dgut.network_engine.web_request

// 基本类型
class BaseResponse<T>(code: Int? = null, message: String? = null) {
    var code: Int? = code

    var data: T? = null
    var message: String? = message
    var ok: Boolean? = null

    override fun toString(): String {
        return "BaseResponse(code=$code, data=$data, message=$message, ok=$ok)"
    }

}