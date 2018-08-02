package cn.leo.aio.service

import java.net.InetSocketAddress
import java.nio.ByteBuffer
import java.nio.channels.AsynchronousSocketChannel

class Channel(var channel: AsynchronousSocketChannel) {
    val host = (channel.remoteAddress as InetSocketAddress).hostString!!
    val buffer = ByteBuffer.allocate(1024)!!
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
        val buffer = ByteBuffer.wrap(data)
        channel.write(buffer, this, writer)
    }

    fun close() {
        try {
            ChannelManager.remove(this)
            channel.close()
        } catch (e: Exception) {

        }
    }
}