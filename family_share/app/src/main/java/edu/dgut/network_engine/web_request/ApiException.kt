package edu.dgut.network_engine.web_request

import android.util.Log
import com.google.gson.JsonParseException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.withContext
import org.apache.http.conn.ConnectTimeoutException
import org.json.JSONException
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

suspend inline fun <T> apiCall(crossinline call: suspend CoroutineScope.() -> BaseResponse<T>): BaseResponse<T> {
    return withContext(Dispatchers.IO) {
        val res: BaseResponse<T>
        try {
            res = call()
        } catch (e: Throwable) {
            Log.e("ApiCaller", "request error", e)
            // 请求出错，将状态码和消息封装为 ResponseResult
            return@withContext ApiException.build(e).toResponse<T>()
        }
        if (res.code == ApiException.CODE_AUTH_INVALID) {
            Log.e("ApiCaller", "request auth invalid")
            // 登录过期，取消协程，跳转登录界面
            // 省略部分代码
            cancel()
        }
        return@withContext res
    }
}

// 网络、数据解析错误处理
class ApiException(
    private val code: Int,
    override val message: String?,
    override val cause: Throwable? = null
) : RuntimeException(message, cause) {
    companion object {
        // 网络状态码
        const val CODE_NET_ERROR = 4000
        const val CODE_TIMEOUT = 4080
        const val CODE_JSON_PARSE_ERROR = 4010
        const val CODE_SERVER_ERROR = 5000

        // 业务状态码
        const val CODE_AUTH_INVALID = 401

        fun build(e: Throwable): ApiException {
            return if (e is HttpException) {
                ApiException(CODE_NET_ERROR, "网络异常(${e.code()},${e.message()})")
            } else if (e is UnknownHostException) {
                ApiException(CODE_NET_ERROR, "网络连接失败，请检查后再试")
            } else if (e is ConnectTimeoutException || e is SocketTimeoutException) {
                ApiException(CODE_TIMEOUT, "请求超时，请稍后再试")
            } else if (e is IOException) {
                ApiException(CODE_NET_ERROR, "网络异常(${e.message})")
            } else if (e is JsonParseException || e is JSONException) {
                // Json解析失败
                ApiException(CODE_JSON_PARSE_ERROR, "数据解析错误，请稍后再试")
            } else {
                ApiException(CODE_SERVER_ERROR, "系统错误(${e.message})")
            }
        }
    }

    fun <T> toResponse(): BaseResponse<T> {
        return BaseResponse(code, message)
    }
}
