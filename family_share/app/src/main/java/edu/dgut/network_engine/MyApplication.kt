package edu.dgut.network_engine

import android.app.Application
import android.content.Context


/**
 * @author Edgar Liu
 *
 * 整个应用程序的 contex
 * 非必要不要使用  可能会造成内存泄漏
 */
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }

    companion object {
        private var context: Context? = null

        /**
         * 获取全局上下文 */
        fun getContext(): Context? {
            return context
        }
    }
}
