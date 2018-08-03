package cn.leo.aio.service

import cn.leo.aio.header.PacketFactory
import cn.leo.aio.utils.Constant
import java.net.InetSocketAddress
import java.nio.ByteBuffer
import java.nio.channels.AsynchronousSocketChannel

class Channel(var channel: AsynchronousSocketChannel) {
    val host = (channel.remoteAddress as InetSocketAddress).hostString!!
    val buffer = ByteBuffer.allocate(Constant.packetSize)!!
    private val reader = Reader()
    private val writer = Writer()

    init {
        read()
    }

    fun read() {
        if (!channel.isOpen) return
        buffer.clear()
        channel.read(buffer, this, reader)
    }


    fun send(msg: String) {
        send(msg.toByteArray())
    }

    fun send(data: ByteArray) {
        if (!channel.isOpen) return
        val bufList = PacketFactory.encodePacketBuffer(data)
        try {
            var len = 0
            bufList.forEach { len += channel.write(it)!!.get() }
            writer.completed(len, this)
        } catch (e: Exception) {
            writer.failed(e, this)
        }
    }

    fun close() {
        try {
            ChannelManager.remove(this)
            channel.close()
        } catch (e: Exception) {

        }
    }
}