package test

import cn.leo.aio.client.AioClient
import java.util.*

object TestClient {
    @JvmStatic
    fun main(args: Array<String>) {
        val client = AioClient()
        client.connect("127.0.0.1", 25678)
        repeat(10000) {
            AioClient().connect("127.0.0.1", 25678)
        }

        while (true) {
            val scanner = Scanner(System.`in`)
            val msg = scanner.next()
            client.send(msg)
        }
    }
}