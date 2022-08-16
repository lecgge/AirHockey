package com.example.airhockey.util

import android.util.Log


/**
 *@Author QiYuZhen
 *
 *
 * 观察日志来辅助我们观察OpenGL ES的运行流程，
 */
object LogU {
   const val TAG="LogU"
    var on =true
    fun v(tag: String= TAG, throwable: Throwable):LogU{
        if (on){
            Log.v(tag,Log.getStackTraceString(throwable))
        }
        return  this
    }
    fun v(tag: String = TAG, message: Any?): LogU {
        if (on) {
            Log.v(tag, message.toString())
        }
        return this
    }

    fun d(tag: String = TAG, throwable: Throwable): LogU {
        if (on) {
            Log.d(tag, Log.getStackTraceString(throwable))
        }
        return this
    }

    fun d(tag: String = TAG, message: Any?): LogU {
        if (on) {
            Log.d(tag, message.toString())
        }
        return this
    }

    fun i(tag: String = TAG, throwable: Throwable): LogU {
        if (on) {
            Log.i(tag, Log.getStackTraceString(throwable))
        }
        return this
    }

    fun i(tag: String = TAG, message: Any?): LogU {
        if (on) {
            Log.i(tag, message.toString())
        }
        return this
    }

    fun w(tag: String = TAG, throwable: Throwable):LogU {
        if (on) {
            Log.w(tag, Log.getStackTraceString(throwable))
        }
        return this
    }

    fun w(tag: String = TAG, message: Any?):LogU {
        if (on) {
            Log.w(tag, message.toString())
        }
        return this
    }

    fun e(tag: String = TAG, throwable: Throwable): LogU {
        if (on) {
            Log.e(tag, Log.getStackTraceString(throwable))
        }
        return this
    }

    fun e(tag: String = TAG, message: Any?): LogU {
        if (on) {
            Log.e(tag, message.toString())
        }
        return this
    }

    fun set(b: Boolean): LogU {
        on = b
        return this
    }
}
