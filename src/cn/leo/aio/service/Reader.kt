package cn.leo.aio.service

import cn.leo.aio.header.PacketFactory
import cn.leo.aio.utils.Logger
import java.nio.channels.CompletionHandler


class Reader : CompletionHandler<Int, Channel> {
    override fun completed(result: Int?, channel: Channel?) {
        /*val buffer = attachment?.buffer
        buffer?.flip()
        val charBuffer = CharBuffer.allocate(Constant.packetSize)
        val decoder = Charset.defaultCharset().newDecoder()
        decoder.decode(buffer, charBuffer, false)
        charBuffer.flip()
        val data = String(charBuffer.array(), 0, charBuffer.limit())*/
        val data = PacketFactory.decodePacketBuffer(channel?.buffer!!).data
        val msg = String(data!!)
        Logger.i("收到客户端发来消息：$msg")
        channel.read()
    }

    override fun failed(exc: Throwable?, channel: Channel?) {
        Logger.e(exc.toString())
        channel?.close()
    }
}