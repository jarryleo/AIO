package cn.leo.aio.service

import cn.leo.aio.utils.Logger
import java.nio.channels.CompletionHandler


class Writer : CompletionHandler<kotlin.Int, Channel> {
    override fun completed(result: Int?, attachment: Channel?) {
        Logger.d("发送成功")
    }

    override fun failed(exc: Throwable?, attachment: Channel?) {
        Logger.e(exc.toString())
        attachment?.close()
    }
}