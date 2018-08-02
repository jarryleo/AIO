package cn.leo.aio.client

import cn.leo.aio.utils.Logger
import java.nio.ByteBuffer
import java.nio.CharBuffer
import java.nio.channels.CompletionHandler
import java.nio.charset.Charset


class Receiver : CompletionHandler<kotlin.Int, AioClient> {
    //接收数据成功，result是数据长度，-1表示异常
    override fun completed(result: Int?, client: AioClient?) {
        val buffer = client?.buffer
        buffer?.flip()
        val charBuffer = CharBuffer.allocate(1024)
        val decoder = Charset.defaultCharset().newDecoder()
        decoder.decode(buffer, charBuffer, false)
        charBuffer.flip()
        val data = String(charBuffer.array(), 0, charBuffer.limit())
        Logger.i("收到服务器发来消息：$data")
        client?.receive() //继续接收下一波数据
    }

    override fun failed(exc: Throwable?, client: AioClient?) {
        client?.close()
        Logger.e(exc.toString())
    }
}