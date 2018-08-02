package cn.leo.aio.service

import cn.leo.aio.client.AioClient
import cn.leo.aio.utils.Logger
import java.nio.channels.CompletionHandler


class Sender : CompletionHandler<Int, AioClient> {
    override fun completed(result: Int?, client: AioClient?) {
        Logger.d("发送成功")
    }

    override fun failed(exc: Throwable?, client: AioClient?) {
        client?.close()
        Logger.e(exc.toString())
    }
}