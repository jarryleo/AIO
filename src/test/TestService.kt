package test

import cn.leo.aio.service.AioServiceCore
import cn.leo.aio.service.Client
import cn.leo.aio.service.ServiceListener
import cn.leo.aio.utils.Logger

object TestService : ServiceListener {

    @JvmStatic
    fun main(args: Array<String>) {
        val service = AioServiceCore()
        service.start(25678, this)
    }

    override fun onNewConnectComing(client: Client) {

    }

    override fun onConnectInterrupt(client: Client) {

    }

    override fun onDataArrived(client: Client, data: ByteArray, cmd: Short) {
        val msg = String(data)
        Logger.d("收到${client.ip}消息:$msg")
    }
}