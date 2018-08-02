package test

import cn.leo.aio.service.AioService

object TestService {
    @JvmStatic
    fun main(args: Array<String>) {
        val service = AioService()
        service.start(25678)

    }
}