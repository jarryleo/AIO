package cn.leo.aio.utils

import java.text.SimpleDateFormat
import java.util.*

object Logger {

    private val currentTime: String
        get() {
            val sdf = SimpleDateFormat("[yyyy-MM-dd HH:mm:ss-SSS]")
            return sdf.format(Date())
        }

    fun i(msg: String) {
        println(currentTime + "Information:" + msg)
    }

    fun d(msg: String) {
        println(currentTime + "Debug:" + msg)
    }

    fun e(msg: String) {
        println(currentTime + "Error:" + msg)
    }
}
