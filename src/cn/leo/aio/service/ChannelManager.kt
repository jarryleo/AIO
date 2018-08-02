package cn.leo.aio.service

import cn.leo.aio.utils.Logger

object ChannelManager {
    private var channelList = ArrayList<Channel>()

    fun add(channel: Channel) {
        channelList.add(channel)
        Logger.d("有客户端接入:${channel.host}")
    }

    fun remove(channel: Channel) {
        Logger.d("有客户端断开:${channel.host}")
        channelList.remove(channel)
    }

    fun sendMsgToAll(data: ByteArray) {
        channelList.forEach { it.send(data) }
    }
}