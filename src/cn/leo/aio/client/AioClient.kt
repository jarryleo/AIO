package cn.leo.aio.client

import cn.leo.aio.header.PacketFactory
import cn.leo.aio.service.Sender
import cn.leo.aio.utils.Constant
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
    val buffer = ByteBuffer.allocate(Constant.packetSize)!!
    private var client: AsynchronousSocketChannel? = null
    private var serverAddress: InetSocketAddress? = null
    private val receiver = Receiver()
    private val sender = Sender()


    fun connect(host: String, port: Int) {
        serverAddress = InetSocketAddress(host, port)
        connect()
    }

    private fun connect() {
        client = AsynchronousSocketChannel.open()
        client?.connect(serverAddress, buffer, handler)
    }

    //连接服务器结果回调
    private val handler =
            object : CompletionHandler<Void, ByteBuffer> {
                override fun completed(p0: Void?, p1: ByteBuffer?) {
                    receive()//开始接收数据
                    Logger.d("连接服务器成功")
                }

                override fun failed(p0: Throwable?, p1: ByteBuffer?) {
                    connect()//重连，这里可以设置重连间隔递增和超时操作
                    Logger.e("连接失败！" + p0.toString())
                }
            }

    //关闭连接
    fun close() {
        try {
            client?.close()
        } catch (e: Exception) {

        }
    }

    //发送文本，默认UTF-8
    fun send(msg: String, cmd: Short = 0) {
        send(msg.toByteArray(), cmd)
    }

    //发送字节数组
    fun send(data: ByteArray, cmd: Short = 0) {
        if (!client?.isOpen!!) return
        val bufList = PacketFactory.encodePacketBuffer(data, cmd)
        try {
            var len = 0
            bufList.forEach { len += client?.write(it)!!.get() }
            sender.completed(len, this)
        } catch (e: Exception) {
            sender.failed(e, this)
        }
    }

    //接收数据
    fun receive() {
        if (!client?.isOpen!!) return
        buffer.clear()
        client?.read(buffer, this, receiver)
    }
}