package cn.leo.aio.client

import cn.leo.aio.header.Packet
import cn.leo.aio.header.PacketFactory
import cn.leo.aio.utils.Logger
import java.nio.ByteBuffer
import java.nio.channels.CompletionHandler


class Receiver : CompletionHandler<Int, AioClient> {
    var cache: Packet? = null

    //接收数据成功，result是数据长度，-1表示异常
    override fun completed(result: Int?, client: AioClient?) {
        if (result!! >= 0) {
            val buffer = client?.buffer
            if (cache == null) {
                cache = PacketFactory.decodePacketBuffer(buffer!!)
            } else {
                val packet = cache!!.addData(buffer!!)
                if (packet != cache) {
                    notifyData(cache!!.data, cache!!.cmd)
                    cache = packet
                }
            }
            //数据包完整后
            if (cache!!.isFull()) {
                notifyData(cache!!.data, cache!!.cmd)
                cache = null
            }
        }
        client?.receive() //继续接收下一波数据
    }

    private fun notifyData(data: ByteArray?, cmd: Short) {
        val msg = String(data!!)
        Logger.i("收到服务器发来消息：$msg")
    }

    override fun failed(exc: Throwable?, client: AioClient?) {
        client?.close()
        Logger.e(exc.toString())
    }
}