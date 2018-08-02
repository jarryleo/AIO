package cn.leo.aio.client

import cn.leo.aio.service.ChannelManager
import cn.leo.aio.service.Sender
import cn.leo.aio.utils.Logger
import java.net.InetSocketAddress
import java.nio.ByteBuffer
import java.nio.channels.AsynchronousSocketChannel
import java.nio.channels.CompletionHandler

/**
 * create by : Jarry Leo
 * date : 2018/7/31 16:33
 */
class AioClient {
    val buffer = ByteBuffer.allocate(1024)!!
    private var client: AsynchronousSocketChannel? = null
    private var serverAddress: InetSocketAddress? = null

    fun connect(host: String, port: Int) {
        serverAddress = InetSocketAddress(host, port)
        connect()
    }

    private fun connect() {
        client = AsynchronousSocketChannel.open()
        client?.connect(serverAddress, buffer, handler)
    }

    private val handler =
            object : CompletionHandler<Void, ByteBuffer> {
                override fun completed(p0: Void?, p1: ByteBuffer?) {
                    receive()
                    Logger.d("连接服务器成功")
                }

                override fun failed(p0: Throwable?, p1: ByteBuffer?) {
                    connect()
                    Logger.e("连接失败！" + p0.toString())
                }
            }

    fun close() {
        try {
            client?.close()
        } catch (e: Exception) {

        }
    }

    fun send(msg: String) {
        send(msg.toByteArray())
    }

    fun send(data: ByteArray) {
        if (!client?.isOpen!!) return
        val buf = ByteBuffer.wrap(data)
        client?.write(buf, this, Sender())
    }

    fun receive() {
        if (!client?.isOpen!!) return
        buffer.clear()
        client?.read(buffer, this, Receiver())
    }
}