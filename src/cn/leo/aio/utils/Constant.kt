package cn.leo.aio.utils

object Constant {
    //传输包大小
    const val packetSize = 1024
    //框架识别
    val magic = byteArrayOf(0x02, 0x05, 0x06)
    //版本号，最低1
    const val version: Byte = 1
    //是否校验框架版本号，版本号不同则断开连接
    const val checkVersion = true
}