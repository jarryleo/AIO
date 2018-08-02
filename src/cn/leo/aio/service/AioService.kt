package cn.leo.aio.service

import cn.leo.aio.utils.Logger
import java.net.InetSocketAddress
import java.nio.ByteBuffer
import java.nio.channels.AsynchronousChannelGroup
import java.nio.channels.AsynchronousServerSocketChannel
import java.nio.channels.AsynchronousSocketChannel
import java.nio.channels.CompletionHandler
import java.util.*
import java.util.concurrent.Executors


class AioService {
    private val executorService = Executors.newFixedThreadPool(80)
    private val channelGroup = AsynchronousChannelGroup.withThreadPool(executorService)
    private val service = AsynchronousServerSocketChannel.open(channelGroup)

    fun start(port: Int) {
        val serverAddress = InetSocketAddress(port)
        try {
            service.bind(serverAddress)
            asyncAccept()
            Logger.i("服务器开启成功(端口号:$port)")
            command()
        } catch (e: Exception) {
            Logger.e(e.toString())
        }
    }

    //异步接入方法
    private fun asyncAccept() {
        val buffer = ByteBuffer.allocate(1024)
        service.accept(buffer, handler)
    }

    //接入回调
    private val handler =
            object : CompletionHandler<AsynchronousSocketChannel, ByteBuffer> {
                override fun completed(client: AsynchronousSocketChannel?, p1: ByteBuffer?) {
                    asyncAccept()
                    val channel = Channel(client!!)
                    ChannelManager.add(channel)
                }

                override fun failed(p0: Throwable?, p1: ByteBuffer?) {
                    Logger.e(p0.toString())
                }
            }

    //主线程阻塞
    private fun command() {
        while (true) {
            val scanner = Scanner(System.`in`)
            val command = scanner.next()
            ChannelManager.sendMsgToAll(command.toByteArray())
        }
    }

}