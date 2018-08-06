package cn.leo.aio.heart

import cn.leo.aio.service.Channel
import cn.leo.aio.service.ChannelManager
import cn.leo.aio.utils.Constant
import java.util.*

object HeartManager : TimerTask() {
    private val lru = LinkedHashMap<Channel, Long>(Constant.maxClient, 0.75f, true)

    override fun run() {
        trim()
    }

    fun reflow(channel: Channel) {
        lru[channel] = channel.heartStamp
    }

    //心跳超时检测
    private fun trim() {
        val iterator = lru.iterator()
        while (iterator.hasNext()) {
            val next = iterator.next()
            val channel = next.key
            val contains = ChannelManager.contains(channel)
            if (!contains) iterator.remove()
            val timestamp = next.value
            if (System.currentTimeMillis() - timestamp > Constant.heartTimeOut) {
                channel.close()
                iterator.remove()
            } else {
                break
            }
        }
    }
}