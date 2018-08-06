package test

import cn.leo.aio.service.ChannelManager
import cn.leo.aio.service.Client
import cn.leo.aio.service.Service
import cn.leo.aio.service.ServiceListener
import cn.leo.aio.utils.Logger
import java.util.*

object TestService : ServiceListener {

    @JvmStatic
    fun main(args: Array<String>) {
        Service.start(25678, this)
        command()
    }

    //主线程阻塞
    private fun command() {
        while (true) {
            val scanner = Scanner(System.`in`)
            val command = scanner.next()
            ChannelManager.sendMsgToAll(command.toByteArray())
        }
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