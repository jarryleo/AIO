package cn.leo.aio.service

import cn.leo.aio.utils.Logger
import java.nio.CharBuffer
import java.nio.channels.CompletionHandler
import java.nio.charset.Charset


class Reader : CompletionHandler<Int, Channel> {
    override fun completed(result: Int?, attachment: Channel?) {
        val buffer = attachment?.buffer
        buffer?.flip()
        val charBuffer = CharBuffer.allocate(1024)
        val decoder = Charset.defaultCharset().newDecoder()
        decoder.decode(buffer, charBuffer, false)
        charBuffer.flip()
        val data = String(charBuffer.array(), 0, charBuffer.limit())
        Logger.i("收到客户端发来消息：$data")
        attachment?.read()
    }

    override fun failed(exc: Throwable?, attachment: Channel?) {
        Logger.e(exc.toString())
        attachment?.close()
    }
}