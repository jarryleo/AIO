package cn.leo.aio.service

import cn.leo.aio.header.Packet
import cn.leo.aio.header.PacketFactory
import cn.leo.aio.utils.Constant
import cn.leo.aio.utils.Logger
import java.nio.channels.CompletionHandler


class Reader : CompletionHandler<Int, Channel> {
    var cache: Packet? = null
    override fun completed(result: Int?, channel: Channel?) {
        if (result!! >= 0) {
            val buffer = channel?.buffer
            if (cache == null) {
                cache = PacketFactory.decodePacketBuffer(buffer!!)
            } else {
                val packet = cache!!.addData(buffer!!)
                if (packet != cache) {
                    notifyData(cache!!.data)
                    cache = packet
                }
            }
            //校验数据包版本，不符合断开连接
            if (Constant.checkVersion && cache!!.ver != Constant.version) {
                channel.close()
                return
            }
            //数据包完整后
            if (cache!!.isFull()) {
                notifyData(cache!!.data)
                cache = null
            }
        }
        channel?.read() //继续接收下一波数据
    }

    private fun notifyData(data: ByteArray?) {
        val msg = String(data!!)
        Logger.i("收到客户端发来消息：$msg")
    }

    override fun failed(exc: Throwable?, channel: Channel?) {
        Logger.e(exc.toString())
        channel?.close()
    }
}