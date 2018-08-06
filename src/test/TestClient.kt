package test

import cn.leo.aio.client.AioClient
import cn.leo.aio.client.ClientListener
import cn.leo.aio.utils.Logger
import java.util.*

object TestClient : ClientListener {


    @JvmStatic
    fun main(args: Array<String>) {
        val client = AioClient()
        client.connect("127.0.0.1", 25678, this)
        while (true) {
            val scanner = Scanner(System.`in`)
            val msg = scanner.next()
            client.send(msg)
        }
    }

    override fun onConnectSuccess() {
    }

    override fun onConnectFailed() {
    }

    override fun onConnectInterrupt() {
    }

    override fun onDataArrived(data: ByteArray, cmd: Short) {
        val msg = String(data)
        Logger.i("收到服务器发来消息：$msg")
    }
}